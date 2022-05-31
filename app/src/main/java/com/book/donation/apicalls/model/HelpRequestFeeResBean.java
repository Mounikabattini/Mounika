package com.book.donation.apicalls.model;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class HelpRequestFeeResBean implements Serializable {

    public void setDetails(String firstName, String lastName, String studentLevel, String studentId, String studentPhone, String studentEmail, String studentCourse,
                           String intituteName, String intituteAddress, String termDetails, String parentName, String parentPhone, String parentEmail, String feeType,
                           String amount, String accountName, String accountNumber, String bankName, String ifsc, String dd_cheque_no, String reason){
        this.firstName = firstName;
        this.lastName = lastName;
        this.studentLevel = studentLevel;
        this.studentId = studentId;
        this.studentPhone = studentPhone;
        this.studentEmail = studentEmail;
        this.studentCourse = studentCourse;
        this.intituteName = intituteName;
        this.intituteAddress = intituteAddress;
        this.termDetails = termDetails;
        this.parentName = parentName;
        this.parentPhone = parentPhone;
        this.parentEmail = parentEmail;
        this.feeType = feeType;
        this.amount = amount;
        this.accountName = accountName;
        this.accountNumber = accountNumber;
        this.bankName = bankName;
        this.ifsc = ifsc;
        this.dd_cheque_no = dd_cheque_no;
        this.reason = reason;
    }

    @SerializedName("first_name")
    private String firstName;

    @SerializedName("last_name")
    private String lastName;

    @SerializedName("student_level")
    private String studentLevel;

    @SerializedName("student_id")
    private String studentId;

    @SerializedName("student_phone")
    private String studentPhone;

    @SerializedName("student_email")
    private String studentEmail;

    @SerializedName("student_course")
    private String studentCourse;

    @SerializedName("intitute_name")
    private String intituteName;

    @SerializedName("intitute_address")
    private String intituteAddress;

    @SerializedName("term_details")
    private String termDetails;

    @SerializedName("parent_name")
    private String parentName;

    @SerializedName("parent_phone")
    private String parentPhone;

    @SerializedName("parent_email")
    private String parentEmail;

    @SerializedName("fee_type")
    private String feeType;

    @SerializedName("amount")
    private String amount;

    @SerializedName("account_name")
    private String accountName;

    @SerializedName("account_number")
    private String accountNumber;

    @SerializedName("bank_name")
    private String bankName;

    @SerializedName("ifsc")
    private String ifsc;

    @SerializedName("reason")
    private String reason;

    @SerializedName("dd_cheque_no")
    private String dd_cheque_no;

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public String getStudentLevel() {
        return studentLevel;
    }

    public String getStudentId() {
        return studentId;
    }

    public String getStudentPhone() {
        return studentPhone;
    }

    public String getStudentEmail() {
        return studentEmail;
    }

    public String getStudentCourse() {
        return studentCourse;
    }

    public String getIntituteName() {
        return intituteName;
    }

    public String getIntituteAddress() {
        return intituteAddress;
    }

    public String getTermDetails() {
        return termDetails;
    }

    public String getParentName() {
        return parentName;
    }

    public String getParentPhone() {
        return parentPhone;
    }

    public String getParentEmail() {
        return parentEmail;
    }

    public String getFeeType() {
        return feeType;
    }

    public String getAmount() {
        return amount;
    }

    public String getAccountName() {
        return accountName;
    }

    public String getAccountNumber() {
        return accountNumber;
    }

    public String getBankName() {
        return bankName;
    }

    public String getIfsc() {
        return ifsc;
    }

    public String getReason() {
        return reason;
    }

    public String getDd_cheque_no(){
        return dd_cheque_no;
    }

}
