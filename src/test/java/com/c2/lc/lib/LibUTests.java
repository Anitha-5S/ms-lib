package com.c2.lc.lib;

import com.c2.lc.lib.security.AesCbcEncryption;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import org.junit.jupiter.api.Test;

import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import java.io.UnsupportedEncodingException;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.time.LocalDateTime;

public class LibUTests extends BaseTest{

    @Test
    public void testJSon() throws UnsupportedEncodingException {
        JsonObject obj = new JsonObject();
        obj.addProperty("index", "jv_mst");
        String param = helper.toJSON(obj);
        System.out.println(helper.getURLEncodedString(param));
        System.out.println(helper.toXML(param));
//        System.out.println(helper.toJSON(para,));
    }

    @Test
    public void testClass() {
        ClassA a = new ClassA();
        a.setBase(0);

        ClassB b = new ClassB();

        System.out.println(a.getBase());
        System.out.println(b.getBase());
    }

    @Test
    public void testUID () throws NoSuchAlgorithmException {
        System.out.println(helper.generate16DigitRandom("csqaure"));
    }

    @Test
    public void testTimeDiff () throws NoSuchAlgorithmException {
        LocalDateTime start = helper.getCurrentTime();
        LocalDateTime end = start.plusSeconds(11);

        System.out.println(helper.timeDiffInMinutes(start, end));
    }

    @Test
    public void testJson() {
        JsonObject data = new JsonObject();
        data.addProperty("sql", "Test SQL");
        data.addProperty("columns", "col1, col2, col3");
        data.add("params", getParams());
        JsonObject root = new JsonObject();
        root.add("data", data);
        System.out.println(helper.toJSON(root));
    }

    private JsonArray getParams() {
        JsonArray arr = new JsonArray();
        JsonObject param1 = new JsonObject();
        param1.addProperty("param1", "param1");
        JsonObject param2 = new JsonObject();
        param2.addProperty("param2", "param2");
        JsonObject param3 = new JsonObject();
        param3.addProperty("param3", "param3");
        arr.add(param1);
        arr.add(param2);
        arr.add(param3);
        return arr;
    }


   @Test
    public void testUUID() throws NoSuchAlgorithmException {
        System.out.println(helper.generateNonce());
    }

    @Test
    public void testDecode() {
        String sql = "\"table\".\"name\"";
        String encoded = helper.getEncodedString(sql);
        System.out.println(encoded);
        System.out.println(helper.getDecodedString(encoded));
    }

    @Test
    public void general() {
        System.out.println( helper.getLocalDateTime("2020-01-01 10:10:10").toString());
    }

    @Test
    public void millisecond() {
        String timestap = "2020-10-10 10:10:10.100";
        JsonObject json = new JsonObject();
        json.addProperty("a", 10);
        json.addProperty("time", timestap);
        ClassA a = helper.fromJson(json, ClassA.class);
        System.out.println( helper.toJson(a));
    }

    @Test
    public void isEmpty() {
        String str = "  ";
        System.out.println(str.isEmpty());
    }

    @Test
    public void testHash() throws InvalidKeyException, NoSuchAlgorithmException {
        //String request = "{\"billIdentifier\":\"1234\",\"plTransactionId\":3369122,\"reversedAmount\":24509,\"reversalTime\":\"2020-04-24T16:50:57.120+0530\",\"reversalReason\":\"VOID\"}";
        String request = helper.getJsonObject("{\"clientId\":\"2534\",\"order_details\":[{\"order_id\":\"20TLF7S3286\",\"invoice_id\":\"20TLF7S3286\",\"order_value\":\"74.8\",\"amount_to_collect\":\"0.0\",\"payment_type\":\"Prepaid\",\"merchant_id\":\"4165168\",\"items\":[{\"category\":\"MEDICINE\",\"item_id\":\"000000\",\"is_food\":\"N\",\"item_name\":\"MEDICINE\",\"unit_price\":\"74.8\",\"quantity\":\"1\",\"itemWiseTotal\":74.8}],\"order_weight\":\"1.000\",\"order_dimension\":[{\"length\":\"1.0000\",\"breadth\":\"1.0000\",\"height\":\"1.0000\"}],\"cust_name\":\"DUSHYANT GOAA\",\"cust_address_line1\":\"HQ CE P SHIVALIK, IDPL, VIRBHADRA, RISHIKESH\",\"cust_address_line2\":\"\",\"cust_landmark\":\"-\",\"cust_contact\":\"7839470833\",\"cust_lat\":\"19.047321\",\"cust_long\":\"73.069908\",\"pincode\":\"249202\",\"order_category\":\"Instant\",\"pickup_time\":\"2021-02-25 17:00:00.000\",\"delivery_time\":\"\",\"delivery_code_required\":\"0\",\"pickup_code_required\":\"0\",\"da_type\":\"1\",\"receiver_details_required\":{\"delivery_signature\":\"0\",\"receiver_name\":\"0\",\"receiver_relation\":\"0\",\"receiver_id_proof_type\":\"0\",\"receiver_id_proof_no\":\"0\",\"receiver_id_proof_expiry\":\"0\"}}],\"dttm\":\"2021-03-03 15:27:15\",\"index\":\"I_CREATE_ORDER\"}").toString();
        System.out.println(helper.generateHMacHash("HmacSHA256", "3490f60cb04a7342c99f165319e92fda6acbf656e43cad6fe0bdb3f49ac6acad", request));
    }

    @Test
    public void testJSON() {
        ClassA a = new ClassA();
        System.out.println(helper.toJson(a));
    }

    @Test
    public void testLDT() {
        System.out.println(helper.getStartOfDay("2020-08-09"));
        System.out.println(helper.getEndOfDay("2020-08-09"));
    }

    @Test
    public void testGetValueFromComplexJson() {
        String[] jsonPaths = {"data.tran_json.doctor.doctorName", "data.tran_json.items[0].batchEntity.isChecked"};
        JsonObject jsonObject = helper.getJsonObject("{ \"c2_code\": \"005\", \"br_code\": \"005\", \"data\": { \"det\": [ { \"c_barcode\": \"\", \"c_batch_no\": \"078J016\", \"c_bom_code\": \"0\", \"c_br_code\": \"001\", \"c_claim_reason\": \"0\", \"c_cont_code\": \"0\", \"c_coupon_code\": \"0\", \"c_coupon_srno\": \"0\", \"c_dc_br_code\": \"\", \"c_dc_prefix\": \"\", \"c_dc_year\": \"\", \"c_eb_doc_no\": \"\", \"c_eb_tray_Code\": \"\", \"c_godown_code\": \"0\", \"c_gst_code\": \"\", \"c_hsn_sac_code\": \"\", \"c_integration_code\": \"0\", \"c_item_code\": \"000025\", \"c_mf_code\": \"-\", \"c_ord_br_code\": \"0\", \"c_ord_prefix\": \"0\", \"c_ord_year\": \"0\", \"c_order_br_code\": \"0\", \"c_order_prefix\": \"\", \"c_order_year\": \"\", \"c_prefix\": \"S\", \"c_print_batch\": \"0\", \"c_quot_no\": \"0\", \"c_remark\": \"\", \"c_remark2\": \"\", \"c_sman_code\": \"0\", \"c_supp_code\": \"\", \"c_tray_code\": \"0\", \"c_year\": \"20\", \"d_date\": \"2020-07-24\", \"d_ldate\": \"2020-07-24\", \"n_2nd_sale\": \"0\", \"n_add_cess_per\": \"0.0\", \"n_add_cess_rev_excl\": \"0\", \"n_adjust\": \"0\", \"n_allow_points\": \"0\", \"n_amount\": \"238.44\", \"n_bom_seq\": \"0\", \"n_cancel_flag\": \"0\", \"n_card_reverse_dis\": \"0\", \"n_cess_amt\": \"0.0\", \"n_cess_per\": \"0.0\", \"n_cgst_amt\": \"12.77\", \"n_cgst_per\": \"6.0\", \"n_claim\": \"0\", \"n_claim_per\": \"0\", \"n_claim_qty\": \"0\", \"n_claim_val\": \"0\", \"n_cst_per\": \"0\", \"n_currency\": \"0\", \"n_dc_pk\": \"0\", \"n_dc_seq\": \"0\", \"n_dc_srno\": \"0\", \"n_disc_per\": \"0.00\", \"n_eb_load\": \"0\", \"n_eb_seq\": \"0\", \"n_eff_pur_rate\": \"0\", \"n_exchange_rate\": \"0\", \"n_free_tax_amt\": \"0\", \"n_from_gst_type\": \"0\", \"n_gst_enabled\": \"1\", \"n_hold_flag\": \"0\", \"n_igst_amt\": \"0.0\", \"n_igst_per\": \"0.0\", \"n_inv_qty\": \"0\", \"n_item_tax_on_sch_qty\": \"0\", \"n_item_tax_suffer\": \"0\", \"n_item_vatts_flag\": \"0\", \"n_item_vatts_mrp\": \"0\", \"n_marketplace_flag\": \"0\", \"n_mrp\": \"23.85\", \"n_ord_pk\": \"0\", \"n_ord_rate\": \"0\", \"n_ord_rate_diff\": \"0\", \"n_ord_seq\": \"0\", \"n_ord_srno\": \"0\", \"n_order_no\": \"0\", \"n_order_seq\": \"0\", \"n_pick_seq\": \"0\", \"n_pk\": \"20001830071\", \"n_points\": \"0\", \"n_print_seq\": \"0\", \"n_purord_pk\": \"0\", \"n_qty\": \"10\", \"n_qty1\": \"0\", \"n_ref_seq\": \"0\", \"n_remind_days\": \"0\", \"n_req_qty\": \"0\", \"n_rst_per\": \"0\", \"n_sale_rate\": \"21.29\", \"n_sch_disc\": \"0\", \"n_sch_qty\": \"0\", \"n_seq\": \"1\", \"n_sgst_amt\": \"12.77\", \"n_sgst_per\": \"6.0\", \"n_share_disc\": \"0\", \"n_shift\": \"0\", \"n_sp_sale_flag\": \"0\", \"n_srno\": \"71\", \"n_st_per\": \"0\", \"n_std_disc_per\": \"0\", \"n_stk_serial\": \"0\", \"n_store_track\": \"0\", \"n_tax_amt\": \"25.54\", \"n_tax_on_sch_qty\": \"0\", \"n_tax_suffer\": \"0.0\", \"n_taxable_amt\": \"212.90\", \"n_to_gst_type\": \"1\", \"n_total_discount\": \"0\", \"n_ts_value\": \"0\", \"n_update_stock\": \"0\", \"n_vaton\": \"0\", \"n_vatts_mrp\": \"0.0\", \"n_verified_qty\": \"0\", \"t_ltime\": \"2020-07-24 11:58:31.892\" } ], \"mst\": { \"c_act_sman_code\": \"0\", \"c_add_cess_act_code\": \"-CESAP\", \"c_amt_act_code\": \"\", \"c_br_code\": \"001\", \"c_card_bank_code\": \"\", \"c_cess_act_code\": \"-CESSP\", \"c_cess_rev_act_code\": \"\", \"c_cgst_act_code\": \"-CGSTP\", \"c_cgst_rev_act_code\": \"-CGSTR\", \"c_computer_name\": \"\", \"c_coupon_code\": \"\", \"c_coupon_srno\": \"\", \"c_credit_act\": \"SAL\", \"c_cst_act_code\": \"\", \"c_currency_code\": \"\", \"c_cust_code\": \"GC01\", \"c_debit_act_code\": \"\", \"c_disc_act_code\": \"\", \"c_discrs_act_code\": \"\", \"c_dman_code\": \"-\", \"c_doctor_address\": \"SD\", \"c_doctor_code\": \"GD01\", \"c_doctor_name\": \"DNN\", \"c_doctor_office_code\": \"\", \"c_email\": \"\", \"c_eway_bill_no\": \"\", \"c_from_gst_no\": \"29ASDFG7354V1Z5\", \"c_igst_act_code\": \"-IGSTP\", \"c_igst_rev_act_code\": \"-IGSTR\", \"c_integration_code\": \"\", \"c_ip_br_code\": \"001\", \"c_ipno\": \"9234567890\", \"c_lr_no\": \"\", \"c_mobile\": \"9234567890\", \"c_modiuser\": \"\", \"c_mr_code\": \"\", \"c_note\": \"\", \"c_ord_br_code\": \"\", \"c_ord_prefix\": \"\", \"c_ord_year\": \"\", \"c_order_id\": \"\", \"c_order_prefix\": \"\", \"c_order_year\": \"\", \"c_oth_act_code\": \"\", \"c_patient\": \"PK TESTING\", \"c_patient_details\": \"\", \"c_pay_ac_code1\": \"CASH\", \"c_pay_ac_code2\": \"\", \"c_pay_ac_code3\": \"\", \"c_pay_ac_code4\": \"\", \"c_pay_ac_code5\": \"\", \"c_pay_code1\": \"CASH\", \"c_pay_code2\": \"\", \"c_pay_code3\": \"\", \"c_pay_code4\": \"\", \"c_pay_code5\": \"\", \"c_port_code\": \"\", \"c_prefix\": \"S\", \"c_print_format_code\": \"\", \"c_purord_no\": \"\", \"c_ref_no\": \"\", \"c_reg_no\": \"\", \"c_remark\": \"\", \"c_service_amt_act_code\": \"0\", \"c_sgst_act_code\": \"-SGSTP\", \"c_sgst_rev_act_code\": \"-SGSTR\", \"c_sman_code\": \"\", \"c_st_act_code\": \"\", \"c_state_code\": \"\", \"c_sys_ip\": \"\", \"c_sys_user\": \"\", \"c_terms_code\": \"\", \"c_to_gst_no\": \"\", \"c_token_no\": \"\", \"c_trader_code\": \"\", \"c_tran_gst_no\": \"\", \"c_tran_type\": \"\", \"c_transport_code\": \"\", \"c_user\": \"ECOLITE\", \"c_vat_act_code\": \"\", \"c_year\": \"20\", \"d_date\": \"2020-07-24\", \"d_ldate\": \"2020-07-24\", \"n_add_cess_amt\": \"0.00\", \"n_admin_settlement\": \"0\", \"n_advance_amt\": \"0\", \"n_approved\": \"0\", \"n_cancel_flag\": \"0\", \"n_card_reverse_dis\": \"0\", \"n_cases\": \"0\", \"n_cess_amt\": \"0.00\", \"n_cgst_amt\": \"12.77\", \"n_cnt_no\": \"0\", \"n_counter_sale\": \"1\", \"n_credit_days\": \"0\", \"n_cst\": \"0\", \"n_day_seq\": \"0\", \"n_disc_rs\": \"0\", \"n_discount\": \"0.00\", \"n_discount_per\": \"0\", \"n_dist_km\": \"0\", \"n_exchange_rate\": \"0\", \"n_from_gst_type\": \"0\", \"n_gst_enabled\": \"1\", \"n_igst_amt\": \"0.00\", \"n_inter_pur\": \"0\", \"n_ip_det_seq\": \"0\", \"n_marketplace_flag\": \"0\", \"n_multi_payment\": \"0\", \"n_non_taxable_ret\": \"0\", \"n_ok\": \"1\", \"n_ord_no\": \"0\", \"n_ord_pk\": \"0\", \"n_order_no\": \"0\", \"n_other_charge\": \"\", \"n_pay_ac1_flag\": \"0\", \"n_pay_ac2_flag\": \"0\", \"n_pay_ac3_flag\": \"0\", \"n_pay_ac4_flag\": \"0\", \"n_pay_ac5_flag\": \"0\", \"n_pay_amt1\": \"238.44\", \"n_pay_amt2\": \"0\", \"n_pay_amt3\": \"0\", \"n_pay_amt4\": \"0\", \"n_pay_amt5\": \"0\", \"n_pcbc_charge\": \"0\", \"n_pending_points\": \"0\", \"n_pk\": \"20001830071\", \"n_point_factor\": \"0\", \"n_point_redeem\": \"0\", \"n_point_value\": \"0\", \"n_points\": \"0\", \"n_round_off\": \"0.0\", \"n_rst\": \"0\", \"n_sale_type\": \"0\", \"n_service_chg\": \"0\", \"n_sgst_amt\": \"12.77\", \"n_share_disc\": \"0\", \"n_shift\": \"0\", \"n_srno\": \"71\", \"n_st\": \"0\", \"n_store_track\": \"0\", \"n_tax_suffer\": \"0\", \"n_taxable_amt\": \"212.90\", \"n_taxable_ret\": \"0\", \"n_tender_amt\": \"0\", \"n_to_gst_type\": \"1\", \"n_total\": \"238.44\", \"n_trader_charge1_total\": \"0\", \"n_trader_charge_total\": \"0\", \"n_trader_comm_total\": \"0\", \"n_update_stock\": \"0\", \"n_urgent\": \"0\", \"n_vaton\": \"0\", \"t_ltime\": \"2020-07-24 11:58:31.892\", \"t_time\": \"11:58:31.892\" }, \"tran_json\": { \"customer\": { \"GSTNo\": \"\", \"StateCode\": \"\", \"add_1\": \"AAA\", \"add_2\": \"Street\", \"add_3\": \"\", \"city\": \"\", \"creditDays\": \"0\", \"deactivatedDate\": \"\", \"discount\": \"0.00\", \"dlNo\": \"\", \"email\": \"\", \"local_cust_code\": \"9234567890\", \"local_cust_name\": \"PK TESTING\", \"lockFlag\": \"0\", \"phone_1\": \"\", \"phone_2\": \"\", \"pin\": \"0\", \"stateName\": \"\" }, \"discount\": \"0.00\", \"doctor\": { \"doctorAddress\": \"SD\", \"doctorName\": \"DNN\" }, \"homeDelivery\": false, \"id\": 5, \"imageDocExists\": false, \"items\": [ { \"additionalCess\": 0, \"additionalCessAmt\": 0, \"batchEntity\": { \"CESSPER\": \"0.00\", \"CGSTPER\": \"6.00\", \"GSTName\": \"GST@ 12%\", \"IGSTPER\": \"12.00\", \"SGSTPER\": \"6.00\", \"additionalCess\": \"0\", \"isChecked\": true, \"qtyPerBox\": 10, \"batch\": \"078J016\", \"expDate\": \"2020-08-01 00:00:00.0\", \"itemCode\": \"000025\", \"mrp\": \"23.85\", \"purRate\": \"15.00\", \"qty\": \"-1270\" }, \"cGst\": 6, \"cGstAmt\": 12.77, \"cess\": 0, \"cessAmt\": 0, \"disc\": \"0.00\", \"discAmt\": 0, \"gstMode\": \"INTRA_STATE\", \"iGst\": 0, \"iGstAmt\": 0, \"isChecked\": false, \"itemEntity\": { \"CESSPER\": \"0.00\", \"CGSTPER\": \"6.00\", \"GSTName\": \"GST@ 12%\", \"IGSTPER\": \"12.00\", \"SGSTPER\": \"6.00\", \"additionalCess\": \"0\", \"gstCode\": \"12\", \"maxRate\": \"23.85\", \"scheduleWarningFlag\": 2, \"stock\": \"-1270\", \"supplierName\": \"\", \"allowPurchaseWithSameBatchWithDifferentRate\": 0, \"allowSalesReturn\": 1, \"categoryCode\": \"CT0001\", \"categoryName\": \"SWALLOW\", \"hsnCode\": \"30049031\", \"itemCode\": \"000025\", \"itemName\": \"1 AL 5MG TAB\", \"lockAllTransaction\": 0, \"lockPurchaseOrder\": 0, \"maxQty\": \"0\", \"maxSaleQty\": \"0\", \"mfacCode\": \"M00877\", \"mfacName\": \"FDC LTD (SELECT)\", \"minQty\": \"0\", \"note\": \"PLAIN\", \"pack\": \"10``S\", \"priorityItem\": 0, \"qtyPerBox\": \"10\", \"rack\": \"\", \"reorderQty\": \"0\", \"scheduleCode\": \"1\", \"shelfLife\": \"0.00\", \"supplierCode\": \"\" }, \"qty\": \"10\", \"sGst\": 6, \"sGstAmt\": 12.77, \"seqNo\": 1, \"taxableTotal\": 212.89999999999998, \"total\": 238.43999999999997, \"tranType\": \"SALES\", \"unitDiscountedTotalRate\": 23.84, \"unitTotalRate\": 23.84 } ], \"rrnTags\": [], \"brCode\": \"001\", \"chequeDate\": \"2020-07-24\", \"docNo\": \"20001830071\", \"merchantGstNo\": \"29ASDFG7354V1Z5\", \"paymentMode\": \"Cash\", \"prefix\": \"S\", \"srNo\": \"71\", \"timeStamp\": { \"year\": 2020, \"month\": 6, \"dayOfMonth\": 24, \"hourOfDay\": 11, \"minute\": 58, \"second\": 31 }, \"tranType\": \"SALES\", \"year\": \"20\" } } }");
        System.out.println(helper.getValueFromComplexJson(jsonPaths[0], jsonObject)); // output : DNN
        System.out.println(helper.getValueFromComplexJson(jsonPaths[1], jsonObject)); // output : true
    }

    @Test
    public void testCred() throws BadPaddingException, InvalidAlgorithmParameterException, UnsupportedEncodingException, IllegalBlockSizeException, NoSuchAlgorithmException, NoSuchPaddingException, InvalidKeyException {
        AesCbcEncryption aesCbcEncryption = new AesCbcEncryption();
        System.out.println(aesCbcEncryption.getDecryptedData("NRuKknzu/oVGdThh8aUX2i+rFPXuXI", "9z$C&F)J@NcRfUjWnZr4u7x!A%D*G-Ka", "fSdtT8WMdzVpXDcM"));
    }



    @Test
    public void testJsonConversion() {
        String str = "{\"data\":{\"n_order_no\":182289,\"c_c2code\":\"700000\",\"c_br_code\":\"TNJ4\",\"c_cancel_flag\":\"N\",\"c_cust_code\":\"C00003\",\"c_order_status\":\"OP\",\"c_source_ref_no\":\"JP151\",\"n_delivery_charge\":0.0,\"n_discount_amount\":75.0,\"c_ipno\":\"939272\",\"n_additional_disc\":50.0,\"n_item_total\":1100.0,\"n_net_payable_amount\":1100.0,\"t_order_created_timestamp\":\"2021-02-10 20:05:33\",\"t_source_timestamp\":\"2021-01-16 14:12:36\",\"n_voucher_amount\":200.0,\"n_wallet_amount\":100.0,\"items\":[{\"n_order_no\":182289,\"n_seq\":1,\"c_cancel_flag\":\"N\",\"c_item_code\":\"I51073\",\"c_presc_flag\":\"N\",\"c_rate_consider_flag\":\"N\",\"c_user_flag\":\"N\",\"n_actual_rate\":0.0,\"n_disc_amount\":0.0,\"n_disc_percentage\":0.0,\"n_item_total\":235.0,\"n_mrp\":80.0,\"n_qty\":5.0,\"n_sale_rate\":50.0,\"n_scheme_per\":0.0,\"n_scheme_qty\":0.0,\"n_qty_per_box\":1.0,\"n_packing_qty\":1.0,\"n_packing_mrp\":80.0},{\"n_order_no\":182289,\"n_seq\":2,\"c_cancel_flag\":\"N\",\"c_item_code\":\"I28807\",\"c_presc_flag\":\"N\",\"c_rate_consider_flag\":\"N\",\"c_user_flag\":\"N\",\"n_actual_rate\":0.0,\"n_disc_amount\":0.0,\"n_disc_percentage\":0.0,\"n_item_total\":235.0,\"n_mrp\":47.0,\"n_qty\":5.0,\"n_sale_rate\":47.0,\"n_scheme_per\":0.0,\"n_scheme_qty\":0.0,\"n_qty_per_box\":5.0,\"n_packing_qty\":1.0,\"n_packing_mrp\":235.0},{\"n_order_no\":182289,\"n_seq\":3,\"c_cancel_flag\":\"N\",\"c_item_code\":\"I52712\",\"c_presc_flag\":\"N\",\"c_rate_consider_flag\":\"N\",\"c_user_flag\":\"N\",\"n_actual_rate\":0.0,\"n_disc_amount\":0.0,\"n_disc_percentage\":0.0,\"n_item_total\":235.0,\"n_mrp\":47.0,\"n_qty\":5.0,\"n_sale_rate\":47.0,\"n_scheme_per\":0.0,\"n_scheme_qty\":0.0,\"n_qty_per_box\":5.0,\"n_packing_qty\":1.0,\"n_packing_mrp\":235.0},{\"n_order_no\":182289,\"n_seq\":4,\"c_cancel_flag\":\"N\",\"c_item_code\":\"I04021\",\"c_presc_flag\":\"N\",\"c_rate_consider_flag\":\"N\",\"c_user_flag\":\"N\",\"n_actual_rate\":0.0,\"n_disc_amount\":0.0,\"n_disc_percentage\":0.0,\"n_item_total\":235.0,\"n_mrp\":47.0,\"n_qty\":5.0,\"n_sale_rate\":47.0,\"n_scheme_per\":0.0,\"n_scheme_qty\":0.0,\"n_qty_per_box\":5.0,\"n_packing_qty\":1.0,\"n_packing_mrp\":235.0},{\"n_order_no\":182289,\"n_seq\":5,\"c_cancel_flag\":\"N\",\"c_item_code\":\"I94832\",\"c_presc_flag\":\"N\",\"c_rate_consider_flag\":\"N\",\"c_user_flag\":\"N\",\"n_actual_rate\":0.0,\"n_disc_amount\":0.0,\"n_disc_percentage\":0.0,\"n_item_total\":235.0,\"n_mrp\":47.0,\"n_qty\":5.0,\"n_sale_rate\":47.0,\"n_scheme_per\":0.0,\"n_scheme_qty\":0.0,\"n_qty_per_box\":5.0,\"n_packing_qty\":1.0,\"n_packing_mrp\":235.0}],\"delivery\":{\"c_mode\":\"\",\"c_name\":\"Dushyant Goaa\",\"c_note\":\"\",\"c_status\":\"\",\"contact\":{\"c_address_1\":\"HQ CE P SHIVALIK, IDPL, VIRBHADRA, RISHIKESH\",\"c_alternative_email_id\":\"dushyantsrivastav@gmail.com\",\"c_alternative_phone_no\":\"7839470833\",\"c_city\":\"Dehradun\",\"c_contact_name\":\"Dushyant Goaa\",\"c_country\":\"in\",\"c_email_id\":\"dushyantsrivastav@gmail.com\",\"c_mobile_no\":\"7839470833\",\"c_note\":\"\",\"c_phone_no\":\"7839470833\",\"c_pin\":\"249202\",\"c_state\":\"UT\"}},\"prescription\":{\"c_doctor_name\":\"ssssss\",\"c_patient_name\":\"DUSHYANT SRIVASTAVA \",\"prescriptionDocs\":[]},\"shipping\":{\"c_mode\":\"\",\"c_name\":\"Dushyant Goaa\",\"c_note\":\"\",\"c_status\":\"\",\"contact\":{\"c_address_1\":\"HQ CE P SHIVALIK, IDPL, VIRBHADRA, RISHIKESH\",\"c_alternative_email_id\":\"dushyantsrivastav@gmail.com\",\"c_alternative_phone_no\":\"7839470833\",\"c_city\":\"Dehradun\",\"c_contact_name\":\"Dushyant Goaa\",\"c_country\":\"in\",\"c_email_id\":\"dushyantsrivastav@gmail.com\",\"c_mobile_no\":\"7839470833\",\"c_note\":\"\",\"c_phone_no\":\"7839470833\",\"c_pin\":\"249202\",\"c_state\":\"UT\"}},\"billing_details\":{\"c_customer_name\":\"Dushyant Goaa\",\"contact\":{\"c_address_1\":\"HQ CE P SHIVALIK, IDPL, VIRBHADRA, RISHIKESH\",\"c_alternative_email_id\":\"dushyantsrivastav@gmail.com\",\"c_alternative_phone_no\":\"7839470833\",\"c_city\":\"Dehradun\",\"c_contact_name\":\"Dushyant Goaa\",\"c_country\":\"in\",\"c_email_id\":\"dushyantsrivastav@gmail.com\",\"c_mobile_no\":\"7839470833\",\"c_note\":\"\",\"c_phone_no\":\"7839470833\",\"c_pin\":\"249202\",\"c_state\":\"UT\"}},\"payment\":[{\"c_payment_ref_code\":\"NMS1807161120506MSO4\",\"c_payment_status\":\"S\",\"c_payment_type\":\"PREP\",\"t_payment_timestamp\":\"2018-07-16 14:12:36\"}],\"coupon_details\":[{\"n_order_no\":182289,\"c_coupon_code\":\"IHOFDGJ3CC\",\"n_coupon_amt\":50.0,\"t_created_timestamp\":\"2021-02-10 20:05:34\"}]}}";
        System.out.println(helper.fromJson(str, Object.class));
    }

    @Test
    public void testNullJsonObject() {
        JsonObject data = new JsonObject();
        data.addProperty("c_name", "C2");
        data.addProperty("d_dob", "");
        data.addProperty("d_date", "2021-01-01");
        data.addProperty("t_dob", "null");
        data.addProperty("n_decimal", 2.9);
        data.addProperty("n_int", 5);
        data = helper.getNullProcessedData(data);
        System.out.println(data.toString());
    }
}
