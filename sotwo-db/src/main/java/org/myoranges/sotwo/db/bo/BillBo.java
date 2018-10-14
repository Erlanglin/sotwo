package org.myoranges.sotwo.db.bo;

import org.myoranges.sotwo.db.domain.SotwoConsumeInfo;
import org.myoranges.sotwo.db.domain.SotwoConsumeLog;

import java.util.List;
import java.util.Map;

public class BillBo {

    private SotwoConsumeLog sotwoConsumeLog;

    private String billStatus;

    private List<ConsumeInfoAndStatus> sotwoConsumeInfoAndStatus;

    public SotwoConsumeLog getSotwoConsumeLog() {
        return sotwoConsumeLog;
    }

    public void setSotwoConsumeLog(SotwoConsumeLog sotwoConsumeLog) {
        this.sotwoConsumeLog = sotwoConsumeLog;
    }

    public String getBillStatus() {
        return billStatus;
    }

    public void setBillStatus(String billStatus) {
        this.billStatus = billStatus;
    }

    public List<ConsumeInfoAndStatus> getSotwoConsumeInfoAndStatus() {
        return sotwoConsumeInfoAndStatus;
    }

    public void setSotwoConsumeInfoAndStatus(List<ConsumeInfoAndStatus> sotwoConsumeInfoAndStatus) {
        this.sotwoConsumeInfoAndStatus = sotwoConsumeInfoAndStatus;
    }
}
