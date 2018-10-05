<%@ page contentType = "text/html; charset=utf-8" pageEncoding = "utf-8" %>
<!-- form content -->
    <form autocomplete = "on" method = "post" action = "Insert.do" enctype = "multipart/form-data" id = "form">
        <!-- 有效期限來源為excel檔案
        <div style="width:100%;">
            <p>有效期限</p>
            <input name = "date" type = "date" id = "expire" required>
        </div>-->
        <div style="width:100%;">
            <p>資料來源</p>
            <select name = "source" id = "source">
                <option value = "國立臺灣科技大學">國立臺灣科技大學</option>
                <option value = "國立臺灣師範大學">國立臺灣師範大學</option>
                <option value = "other">其他</option>
            </select>
        </div>
        <div style="width: 100%">
            <div class="custom-control custom-checkbox" id="type_list">讀者類型:
                <div class="custom-control custom-checkbox custom-control-inline">
                    <input type="checkbox" class="custom-control-input" name="type" id="type_NTUST" value="69" checked disabled>
                    <label class="custom-control-label" for="type_NTUST">69</label>
                </div>
                <div class="custom-control custom-checkbox custom-control-inline">
                    <input type="checkbox" class="custom-control-input" name="type" id="type_NTNU" value="66" disabled>
                    <label class="custom-control-label" for="type_NTNU">66</label>
                </div>
            </div>
        </div>
        <div style="width: 100%">
            <div class="custom-control custom-checkbox" id="typeCode_list">讀者代碼:
                <div class="custom-control custom-checkbox custom-control-inline">
                    <input type="checkbox" class="custom-control-input" name="typeCode" id="typeCode_NTUST" value="112" checked disabled>
                    <label class="custom-control-label" for="typeCode_NTUST">112</label>
                </div>
                <div class="custom-control custom-checkbox custom-control-inline">
                    <input type="checkbox" class="custom-control-input" name="typeCode" id="typeCode_NTNU" value="117" disabled>
                    <label class="custom-control-label" for="typeCode_NTNU">117</label>
                </div>
            </div>
        </div>

        <div class="row" style="display: none;">
            類型:<input name = 'type' type = 'text' id = 'type_other' class="col" value="otherType" required>
            代碼:<input name = 'typeCode' type = 'text' id = 'typeCode_other' class="col" value="otherCode" required>
        </div>

        <div style="width:100%;">
            <p>匯入檔案</p>
            <input name = "excel" type = "file" accept="application/vnd.ms-excel, application/vnd.openxmlformats-officedocument.spreadsheetml.sheet">
        </div>
        <p>
            <input type = "submit" value = "submit" id = "submit"/>
        </p>
        <div class = "progress" style="display: none">
            <div class="progress-bar progress-bar-striped progress-bar-animated" role="progressbar"></div>
        </div>

        <div id = "status" class="text-center text-monospace">
            <p></p>
        </div>
    </form>
