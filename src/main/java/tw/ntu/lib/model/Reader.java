package tw.ntu.lib.model;

import org.hibernate.validator.constraints.Length;

import javax.persistence.*;
import javax.validation.constraints.Future;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.sql.Date;
import java.sql.Timestamp;

@Entity
@Table(name = "patron_test", schema = "alma")
public class Reader {
    private String seq;
    private String cname;
    private String college;
    private String deptcode;
    private String deptname;
    private String title;
    private String grade;
    private String gender;
    private Date birthday;
    private String idno;
    private String barcode;
    private String inStatus;
    private Byte expire;
    private String ntuemail;
    private String contemail;
    private String ntuacct;
    private String phonehome;
    private String mobile;
    private String phone3;
    private String nation;
    private String permPostco;
    private String permadd;
    private String contPostco;
    private String contadd;
    private Date begindate;
    private Date enddate;
    private String extend;
    private String src;
    private String usrNote;
    private Timestamp createdate;
    private Timestamp updatetime;
    private String outputType;


    public Reader() {
    }

    @Id
    @NotBlank(message = "「學號」不可以為空")
    @Column(name = "seq")
    public String getSeq() {
        return seq;
    }

    public void setSeq(String seq) {
        this.seq = seq;
    }

    @Basic
    @NotBlank(message = "「姓名」不可以為空")
    @Length(max = 50, message = "「姓名」欄位過長")
    @Column(name = "cname")
    public String getCname() {
        return cname;
    }

    public void setCname(String cname) {
        this.cname = cname;
    }

    @Basic
    @Column(name = "college")
    @NotBlank(message = "「學院」不可以為空")
    @Length(max = 30, message = "「學院」長度過長")
    public String getCollege() {
        return college;
    }

    public void setCollege(String college) {
        this.college = college;
    }

    @Basic
    @Column(name = "deptcode")
    public String getDeptcode() {
        return deptcode;
    }

    public void setDeptcode(String deptcode) {
        this.deptcode = deptcode;
    }

    @Basic
    @Column(name = "deptname")
    @Length(max = 30, message = "「系所」長度過長")
    public String getDeptname() {
        return deptname;
    }

    public void setDeptname(String deptname) {
        this.deptname = deptname;
    }

    @Basic
    @Column(name = "title")
    @NotBlank(message = "「職稱」不可以為空")
    @Length(max = 30, message = "「職稱」長度過長")
    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    @Basic
    @Column(name = "grade")
    public String getGrade() {
        return grade;
    }

    public void setGrade(String grade) {
        this.grade = grade;
    }

    @Basic
    @Column(name = "gender")
    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    @Basic
    @Column(name = "birthday")
    public Date getBirthday() {
        return birthday;
    }

    public void setBirthday(Date birthday) {
        this.birthday = birthday;
    }

    @Basic
    @Column(name = "idno")
    @NotBlank(message = "「身分證號」不可為空")
    @Length(max = 30, message = "「身分證號」不符合")
    public String getIdno() {
        return idno;
    }

    public void setIdno(String idno) {
        this.idno = idno;
    }

    @Basic
    @Column(name = "barcode")
    public String getBarcode() {
        return barcode;
    }

    public void setBarcode(String barcode) {
        this.barcode = barcode;
    }

    @Basic
    @Column(name = "in_status")
    public String getInStatus() {
        return inStatus;
    }

    public void setInStatus(String inStatus) {
        this.inStatus = inStatus;
    }

    @Basic
    @NotNull(message = "請輸入狀態")
    @Column(name = "expire")
    public Byte getExpire() {
        return expire;
    }

    public void setExpire(Byte expire) {
        this.expire = expire;
    }

    @Basic
    @Column(name = "ntuemail")
    public String getNtuemail() {
        return ntuemail;
    }

    public void setNtuemail(String ntuemail) {
        this.ntuemail = ntuemail;
    }

    @Basic
    @Length(max = 50, message = "Email長度過長")
    @Column(name = "contemail")
    public String getContemail() {
        return contemail;
    }

    public void setContemail(String contemail) {
        this.contemail = contemail;
    }

    @Basic
    @Column(name = "ntuacct")
    public String getNtuacct() {
        return ntuacct;
    }

    public void setNtuacct(String ntuacct) {
        this.ntuacct = ntuacct;
    }

    @Basic
    @Length(max = 20, message = "「電話」長度過長")
    @Column(name = "phonehome")
    public String getPhonehome() {
        return phonehome;
    }

    public void setPhonehome(String phonehome) {
        this.phonehome = phonehome;
    }

    @Basic
    @Length(max = 20, message = "「手機」長度過長")
    @Column(name = "mobile")
    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    @Basic
    @Column(name = "phone3")
    public String getPhone3() {
        return phone3;
    }

    public void setPhone3(String phone3) {
        this.phone3 = phone3;
    }

    @Basic
    @Column(name = "nation")
    public String getNation() {
        return nation;
    }

    public void setNation(String nation) {
        this.nation = nation;
    }

    @Basic
    @Column(name = "perm_postco")
    public String getPermPostco() {
        return permPostco;
    }

    public void setPermPostco(String permPostco) {
        this.permPostco = permPostco;
    }

    @Basic
    @Column(name = "permadd")
    public String getPermadd() {
        return permadd;
    }

    public void setPermadd(String permadd) {
        this.permadd = permadd;
    }

    @Basic
    @Column(name = "cont_postco")
    public String getContPostco() {
        return contPostco;
    }

    public void setContPostco(String contPostco) {
        this.contPostco = contPostco;
    }

    @Basic
    @Column(name = "contadd")
    public String getContadd() {
        return contadd;
    }

    public void setContadd(String contadd) {
        this.contadd = contadd;
    }

    @Basic
    @Column(name = "begindate")
    public Date getBegindate() {
        return begindate;
    }

    public void setBegindate(Date begindate) {
        this.begindate = begindate;
    }

    @Basic
    @Column(name = "enddate")
    public Date getEnddate() {
        return enddate;
    }

    public void setEnddate(Date enddate) {
        this.enddate = enddate;
    }

    @Basic
    @Column(name = "extend")
    public String getExtend() {
        return extend;
    }

    public void setExtend(String extend) {
        this.extend = extend;
    }

    @Basic
    @Length(max = 8, message = "「來源」名稱長度過長")
    @Column(name = "src")
    public String getSrc() {
        return src;
    }

    public void setSrc(String src) {
        this.src = src;
    }

    @Basic
    @Length(max = 255, message = "「備註」長度過長")
    @Column(name = "usr_note")
    public String getUsrNote() {
        return usrNote;
    }

    public void setUsrNote(String usrNote) {
        this.usrNote = usrNote;
    }

    @Basic
    @Column(name = "createdate")
    public Timestamp getCreatedate() {
        return createdate;
    }

    public void setCreatedate(Timestamp createdate) {
        this.createdate = createdate;
    }

    @Basic
    @Column(name = "updatetime")
    public Timestamp getUpdatetime() {
        return updatetime;
    }

    public void setUpdatetime(Timestamp updatetime) {
        this.updatetime = updatetime;
    }

    @Basic
    @Column(name = "output_type")
    public String getOutputType() {
        return outputType;
    }

    public void setOutputType(String outputType) {
        this.outputType = outputType;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Reader reader = (Reader) o;

        if (seq != null ? !seq.equals(reader.seq) : reader.seq != null) return false;
        if (cname != null ? !cname.equals(reader.cname) : reader.cname != null) return false;
        if (college != null ? !college.equals(reader.college) : reader.college != null) return false;
        if (deptcode != null ? !deptcode.equals(reader.deptcode) : reader.deptcode != null) return false;
        if (deptname != null ? !deptname.equals(reader.deptname) : reader.deptname != null) return false;
        if (title != null ? !title.equals(reader.title) : reader.title != null) return false;
        if (grade != null ? !grade.equals(reader.grade) : reader.grade != null) return false;
        if (gender != null ? !gender.equals(reader.gender) : reader.gender != null) return false;
        if (birthday != null ? !birthday.equals(reader.birthday) : reader.birthday != null) return false;
        if (idno != null ? !idno.equals(reader.idno) : reader.idno != null) return false;
        if (barcode != null ? !barcode.equals(reader.barcode) : reader.barcode != null) return false;
        if (inStatus != null ? !inStatus.equals(reader.inStatus) : reader.inStatus != null) return false;
        if (expire != null ? !expire.equals(reader.expire) : reader.expire != null) return false;
        if (ntuemail != null ? !ntuemail.equals(reader.ntuemail) : reader.ntuemail != null) return false;
        if (contemail != null ? !contemail.equals(reader.contemail) : reader.contemail != null) return false;
        if (ntuacct != null ? !ntuacct.equals(reader.ntuacct) : reader.ntuacct != null) return false;
        if (phonehome != null ? !phonehome.equals(reader.phonehome) : reader.phonehome != null) return false;
        if (mobile != null ? !mobile.equals(reader.mobile) : reader.mobile != null) return false;
        if (phone3 != null ? !phone3.equals(reader.phone3) : reader.phone3 != null) return false;
        if (nation != null ? !nation.equals(reader.nation) : reader.nation != null) return false;
        if (permPostco != null ? !permPostco.equals(reader.permPostco) : reader.permPostco != null) return false;
        if (permadd != null ? !permadd.equals(reader.permadd) : reader.permadd != null) return false;
        if (contPostco != null ? !contPostco.equals(reader.contPostco) : reader.contPostco != null) return false;
        if (contadd != null ? !contadd.equals(reader.contadd) : reader.contadd != null) return false;
        if (begindate != null ? !begindate.equals(reader.begindate) : reader.begindate != null) return false;
        if (enddate != null ? !enddate.equals(reader.enddate) : reader.enddate != null) return false;
        if (extend != null ? !extend.equals(reader.extend) : reader.extend != null) return false;
        if (src != null ? !src.equals(reader.src) : reader.src != null) return false;
        if (usrNote != null ? !usrNote.equals(reader.usrNote) : reader.usrNote != null) return false;
        if (createdate != null ? !createdate.equals(reader.createdate) : reader.createdate != null) return false;
        if (updatetime != null ? !updatetime.equals(reader.updatetime) : reader.updatetime != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = seq != null ? seq.hashCode() : 0;
        result = 31 * result + (cname != null ? cname.hashCode() : 0);
        result = 31 * result + (college != null ? college.hashCode() : 0);
        result = 31 * result + (deptcode != null ? deptcode.hashCode() : 0);
        result = 31 * result + (deptname != null ? deptname.hashCode() : 0);
        result = 31 * result + (title != null ? title.hashCode() : 0);
        result = 31 * result + (grade != null ? grade.hashCode() : 0);
        result = 31 * result + (gender != null ? gender.hashCode() : 0);
        result = 31 * result + (birthday != null ? birthday.hashCode() : 0);
        result = 31 * result + (idno != null ? idno.hashCode() : 0);
        result = 31 * result + (barcode != null ? barcode.hashCode() : 0);
        result = 31 * result + (inStatus != null ? inStatus.hashCode() : 0);
        result = 31 * result + (expire != null ? expire.hashCode() : 0);
        result = 31 * result + (ntuemail != null ? ntuemail.hashCode() : 0);
        result = 31 * result + (contemail != null ? contemail.hashCode() : 0);
        result = 31 * result + (ntuacct != null ? ntuacct.hashCode() : 0);
        result = 31 * result + (phonehome != null ? phonehome.hashCode() : 0);
        result = 31 * result + (mobile != null ? mobile.hashCode() : 0);
        result = 31 * result + (phone3 != null ? phone3.hashCode() : 0);
        result = 31 * result + (nation != null ? nation.hashCode() : 0);
        result = 31 * result + (permPostco != null ? permPostco.hashCode() : 0);
        result = 31 * result + (permadd != null ? permadd.hashCode() : 0);
        result = 31 * result + (contPostco != null ? contPostco.hashCode() : 0);
        result = 31 * result + (contadd != null ? contadd.hashCode() : 0);
        result = 31 * result + (begindate != null ? begindate.hashCode() : 0);
        result = 31 * result + (enddate != null ? enddate.hashCode() : 0);
        result = 31 * result + (extend != null ? extend.hashCode() : 0);
        result = 31 * result + (src != null ? src.hashCode() : 0);
        result = 31 * result + (usrNote != null ? usrNote.hashCode() : 0);
        result = 31 * result + (createdate != null ? createdate.hashCode() : 0);
        result = 31 * result + (updatetime != null ? updatetime.hashCode() : 0);
        return result;
    }

}
