package com.mycompany.bank.model;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public enum transactionType {
    TOPUP,
    DEBIT,
    CREDIT
}
