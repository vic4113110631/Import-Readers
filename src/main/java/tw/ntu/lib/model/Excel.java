package tw.ntu.lib.model;

import me.xdrop.fuzzywuzzy.FuzzySearch;
import org.apache.commons.validator.routines.EmailValidator;
import org.apache.poi.ss.usermodel.*;
import org.hibernate.*;

import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.stream.Collectors;

public class Excel {
    private List<String> info;
    private HashSet<String> seqList;
    private String source;
    private static int COLLEGE = 5;
    EmailValidator eamilValidator = EmailValidator.getInstance();

    public List<String> getInfo() {
        return info;
    }

    public void setInfo(List<String> info) {
        this.info = info;
    }

    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
    }

    public Excel(String source) {
        this.info = new ArrayList<>();
        this.seqList = new HashSet<>();
        this.source = source;
    }

    public Boolean process(Workbook workbook) {
        Sheet sheet = workbook.getSheetAt(0); // Default to fisrt sheet

        List<Reader> readerList = new ArrayList<>();

        for(int i = 1; i <= sheet.getLastRowNum(); i++){ // Pass first row
            Row row = sheet.getRow(i);
            // Limit.1: Excel has empty rows.
            if(row == null){
                info.add("Row " + i + " is empty");
                return false;
            }

            // Limit.2: Reader format is not correct.
            Reader reader = new Reader();
            for (Cell cell : row) {
                boolean status = setCell(reader, cell);
                if(!status)
                    return false;
            }

            // Limit.3:Check duplicate instance
            Session session = HibernateUtil.openSession();
            Reader temp =  (Reader) session.get(Reader.class, reader.getSeq());
            if(temp != null){
                info.add("Row:"+ i + " -- 條碼" + reader.getSeq() + " 已存在資料庫中");
                return false;
            }

            // Limit.4:Validate reader data
            List<String> errors = Validate.validate(reader, i);
            info.addAll(errors);
            if (!errors.isEmpty())
                return false;

            if(!seqList.add(reader.getSeq())) {
                info.add("檔案中有兩筆資料重複 - 條碼:" + reader.getSeq());
                return false;
            }else {
                    readerList.add(reader);
            }

        } // end for-loop for rows

        Session session = HibernateUtil.openSession();
        Transaction transaction = session.beginTransaction();

        java.util.Date date = Calendar.getInstance().getTime();
        java.sql.Date now = new java.sql.Date(date.getTime());

        Reader temp = null;
        try {
            for (int i = 0; i < readerList.size(); i++) {
                Reader item = readerList.get(i);
                temp = item;

                // 設定起始日、資料建立日/更新日、來源
                item.setBegindate(now);
                item.setCreatedate(new Timestamp(date.getTime()));
                item.setUpdatetime(new Timestamp(date.getTime()));
                item.setSrc(source);

                session.save(item);
                if (i %100 == 0) {
                    // flush a batch of inserts and release memory:
                    session.flush();
                    session.clear();
                }
            }

            transaction.commit();
            info.add("成功匯入" + readerList.size() + "筆資料");

        }catch(NonUniqueObjectException e){
            info.add("重複條碼"+ temp.getSeq() + "已經存在資料庫中");
            e.printStackTrace();
            transaction.rollback();
            return false;
        } catch(TransactionException|SessionException e){
            info.add("Transaction Commit fail!請洽管理員!");
            e.printStackTrace();
            transaction.rollback();
            return false;
        }finally{
            session.close();
        }

        return true;
    } // end method

    public boolean checkSource(Workbook workbook) {
        Sheet sheet = workbook.getSheetAt(0);
        Row row = sheet.getRow(1);

        if(row != null){
            row.getCell(COLLEGE).setCellType(CellType.STRING);
            String college = row.getCell(COLLEGE).getStringCellValue();
            college.replaceAll("台", "臺");

            if(FuzzySearch.ratio(source, college) > 50){
                return true;
            }else {
                if(college.equals("")) {
                    info.add("Row 1: 學院資料為空");
                }else{
                    info.add("資料來源為:" + source + "，檔案中學院為:" + college);
                }
                return false;
            }
        }else{
            info.add("Error!Excel has blank rows!");
            return false;
        }
    }// end method

    private boolean setCell(Reader reader, Cell cell) {
        cell.setCellType(CellType.STRING);
        String value = cell.getStringCellValue();
        switch (cell.getColumnIndex()) {
            case 0: // 學號/員編
                reader.setSeq(value);
                break;
            case 1: // 姓名
                reader.setCname(value);
                break;
            case 2: // 身分證號
                reader.setIdno(value);
                break;
            case 3: // 手機
                reader.setMobile(value);
                break;
            case 4: // 電話
                reader.setPhonehome(value);
                break;
            case 5: //  學院
                reader.setCollege(value);
                break;
            case 6: //  系所
                reader.setDeptname(value);
                break;
            case 7: //  職稱
                reader.setTitle(value);
                break;
            case 8: //  Email
                if(value.equals("")) // Don't Check value when value is empty.
                    return true;

                List<String> emails = new ArrayList<>();
                if(value.contains(",")){
                    emails = Arrays.asList(value.split(","));
                }else{
                    emails.add(value);
                }
                // Check email format
                for(String email : emails) {
                    if (!eamilValidator.isValid(email)) {
                        info.add("條碼:" + reader.getSeq() + " - email欄位不合法!");
                        return false;
                    }
                }
                value = String.join("/", emails);
                reader.setContemail(value);
                break;
            case 9: //  性別
                String gender = "M";
                if(value.contains("女")|| value.toLowerCase().contains("f"))
                    gender = "F";

                reader.setGender(gender);
                break;
            case 10://  狀態
                if (value.equals("-")){
                    reader.setExpire((byte) 0);
                }else{
                    reader.setExpire((byte) 1);
                }
                break;
            case 11://  到期日
                if(value.equals(""))    // Don't Check value when value is empty.
                    return true;

                SimpleDateFormat format = new SimpleDateFormat("yyyyMMddHHmmss");
                Date parsed;
                try {
                    parsed = format.parse(value);
                } catch (ParseException e) {
                    info.add("條碼:" + reader.getSeq() + " - 不可以解析'到期日'");
                    return false;
                }
                reader.setEnddate(new java.sql.Date(parsed.getTime()));

                break;
            case 12://  備註
                reader.setUsrNote(value);
                break;
            default:
                System.out.println("Value is not within fields.");
                break;
        } // end switch
        return true;
    }

    /*
    public List<Integer> FieldMapping(Row row) {
        List<String> fields = Arrays.asList("條碼", "姓名", "身分證號", "手機", "電話",
                "學院", "系所", "職稱", "Email", "性別",
                "日期起" ,"到期日", "備註");

        // Initilize mapping with zeros and fields' size
        mapping = new ArrayList<>(Collections.nCopies(fields.size(), 0));

        for(Cell cell: row){
            cell.setCellType(CellType.STRING);

            int indexOf = fields.indexOf(cell.getStringCellValue());
            if(indexOf == -1){ // Error: Field can not found
                mapping.clear();
                break;
            }
            mapping.set(cell.getColumnIndex(), fields.indexOf(cell.getStringCellValue()));
        } // end for-loop
        return mapping;
    }
    */

}