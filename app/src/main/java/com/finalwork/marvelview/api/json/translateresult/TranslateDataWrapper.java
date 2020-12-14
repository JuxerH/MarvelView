package com.finalwork.marvelview.api.json.translateresult;



import java.util.List;

public class TranslateDataWrapper {
//    from	string	源语言	返回用户指定的语言，或者自动检测出的语种（源语言设为 auto 时）
//    to	string	目标语言	返回用户指定的目标语言
//    trans_result	array	翻译结果	返回翻译结果，包括 src 和 dst 字段
//    trans_result.*.src	string	原文
//    trans_result.*dst	string	译文
    String from;
    String to;
    List<TranslateDataContainer> trans_result;
    int error_code;
    String error_msg;

    public String getFrom() {
        return from;
    }

    public void setFrom(String from) {
        this.from = from;
    }

    public String getTo() {
        return to;
    }

    public void setTo(String to) {
        this.to = to;
    }

    public List<TranslateDataContainer> getTrans_result() {
        return trans_result;
    }

    public void setTrans_result(List<TranslateDataContainer> trans_result) {
        this.trans_result = trans_result;
    }

    public int getError_code() {
        return error_code;
    }

    public void setError_code(int error_code) {
        this.error_code = error_code;
    }
    @Override
    public String toString(){
        return "from:"+from+",to:"+to+",trans_result:"+trans_result;
    }

    public String getError_msg() {
        return error_msg;
    }

    public void setError_msg(String error_msg) {
        this.error_msg = error_msg;
    }
}
