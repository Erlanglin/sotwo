package org.myoranges.sotwo.db.util;

import org.myoranges.sotwo.db.domain.SotwoOrder;

import java.util.ArrayList;
import java.util.List;

/*
 * 订单流程：下单成功－》支付订单－》发货－》收货
 * 订单状态：
 * 101 订单生成，未支付；102，下单未支付用户取消；103，下单未支付超期系统自动取消
 * 201 支付完成，商家未发货；202，订单生产，已付款未发货，却取消
 * 301 商家发货，用户未确认；
 * 401 用户确认收货，订单结束； 402 用户没有确认收货，但是快递反馈已收获后，超过一定时间，系统自动确认收货，订单结束。
 *
 * 当101用户未付款时，此时用户可以进行的操作是取消订单，或者付款操作
 * 当201支付完成而商家未发货时，此时用户可以取消订单并申请退款
 * 当301商家已发货时，此时用户可以有确认收货的操作
 * 当401用户确认收货以后，此时用户可以进行的操作是删除订单，评价商品，或者再次购买
 * 当402系统自动确认收货以后，此时用户可以删除订单，评价商品，或者再次购买
 *
 * 目前不支持订单退货
 */
public class ConsumeUtil {

    public static final Short STATUS_CREATE = 101;
    public static final Short STATUS_PAY = 201;
    public static final Short STATUS_SHIP = 301;
    public static final Short STATUS_CONFIRM = 401;
    public static final Short STATUS_CANCEL = 102;
    public static final Short STATUS_AUTO_CANCEL = 103;
    public static final Short STATUS_REFUND = 202;
    public static final Short STATUS_AUTO_CONFIRM = 402;


    public static String orderStatusText(SotwoOrder order) {
        int status = order.getOrderStatus().intValue();

        if (status == 101) {
            return "未付款";
        }

        if (status == 102) {
            return "已取消";
        }

        if (status == 103) {
            return "已取消(系统)";
        }

        if (status == 201) {
            return "已付款";
        }

        if (status == 202) {
            // 进一步跟踪退款状态
            return "已取消，退款中";
        }

        if (status == 301) {
            return "已发货";
        }

        if (status == 401) {
            return "已收货";
        }

        if (status == 402) {
            return "已收货(系统)";
        }

        throw new IllegalStateException("orderStatus不支持");
    }

    public static Integer consumeStatus(Integer showType){
        // 全部订单
        if (showType == -1) {
            return null;
        }
        if (showType.equals(0)) {
            // 未结算账单
            return 0;
        }
        else if (showType.equals(1)) {
            // 未结算账单
            return 1;
        }
        else {
            return null;
        }
    }


    public static boolean isPayStatus(SotwoOrder SotwoOrder) {
        return ConsumeUtil.STATUS_PAY == SotwoOrder.getOrderStatus().shortValue();
    }

    public static boolean isShipStatus(SotwoOrder SotwoOrder) {
        return ConsumeUtil.STATUS_SHIP == SotwoOrder.getOrderStatus().shortValue();
    }

    public static String billStatusText(Integer status) {
        if(status == 0)
            return "未结算";
        else if(status == 1)
            return "已结算";
        else
            return "不明状态";
    }

    public static String payStatusText(Integer status) {
        if(status == 0)
            return "未付款";
        else if(status == 1)
            return "已付款";
        else
            return "不明状态";
    }

}
