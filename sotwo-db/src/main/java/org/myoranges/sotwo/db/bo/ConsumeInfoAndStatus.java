package org.myoranges.sotwo.db.bo;

import org.myoranges.sotwo.db.domain.SotwoConsumeInfo;
import org.myoranges.sotwo.db.domain.SotwoConsumeLog;

import java.util.List;
import java.util.Map;

public class ConsumeInfoAndStatus {

    private SotwoConsumeInfo sotwoConsumeInfo;

    private String consumeName;

    private String payStatus;

    public ConsumeInfoAndStatus(SotwoConsumeInfo sotwoConsumeInfo, String consumeName,String payStatus) {
        this.sotwoConsumeInfo = sotwoConsumeInfo;
        this.consumeName = consumeName;
        this.payStatus = payStatus;
    }

    public String getConsumeName() {
        return consumeName;
    }

    public void setConsumeName(String consumeName) {
        this.consumeName = consumeName;
    }

    public SotwoConsumeInfo getSotwoConsumeInfo() {
        return sotwoConsumeInfo;
    }

    public void setSotwoConsumeInfo(SotwoConsumeInfo sotwoConsumeInfo) {
        this.sotwoConsumeInfo = sotwoConsumeInfo;
    }

    public String getPayStatus() {
        return payStatus;
    }

    public void setPayStatus(String payStatus) {
        this.payStatus = payStatus;
    }
}
