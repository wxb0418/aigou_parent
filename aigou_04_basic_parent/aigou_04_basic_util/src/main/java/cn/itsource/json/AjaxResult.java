package cn.itsource.json;

public class AjaxResult {

    public static void main(String[] args) {
        AjaxResult ajaxResult = me().setSuccess(true).setMsg("操作成功！");
        System.out.println(ajaxResult);
    }
    private Boolean success = true;
    private String msg = "操作成功";

    private Object object;

    public static AjaxResult me(){
        return new AjaxResult();
    }

    public Boolean getSuccess() {
        return success;
    }

    public AjaxResult setSuccess(Boolean success) {
        this.success = success;
        return this;
    }

    public String getMsg() {
        return msg;
    }

    public AjaxResult setMsg(String msg) {
        this.msg = msg;
        return this;
    }

    public Object getObject() {
        return object;
    }

    public AjaxResult setObject(Object object) {
        this.object = object;
        return this;
    }

    @Override
    public String toString() {
        return "AjaxResult{" +
                "success=" + success +
                ", msg='" + msg + '\'' +
                ", object=" + object +
                '}';
    }
}
