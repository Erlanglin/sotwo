package org.myoranges.sotwo.db.model;

import org.myoranges.sotwo.db.domain.SotwoConsumeInfo;
import org.myoranges.sotwo.db.domain.SotwoConsumeLog;

import java.util.List;

/**
 * Created by Administrator on 2018/6/2/002.
 */
public class SotwoConsumeModel
{

    private SotwoConsumeLog consumeLog;

    private List<SotwoConsumeInfo> sotwoConsumeInfos;

    public SotwoConsumeLog getConsumeLog() {
        return consumeLog;
    }

    public void setConsumeLog(SotwoConsumeLog consumeLog) {
        this.consumeLog = consumeLog;
    }

    public List<SotwoConsumeInfo> getSotwoConsumeInfos() {
        return sotwoConsumeInfos;
    }

    public void setSotwoConsumeInfos(List<SotwoConsumeInfo> sotwoConsumeInfos) {
        this.sotwoConsumeInfos = sotwoConsumeInfos;
    }

    @Override
    public String toString() {
        return "SotwoConsumeModel{" +
                "consumeLog=" + consumeLog +
                ", sotwoConsumeInfos=" + sotwoConsumeInfos +
                '}';
    }
}
