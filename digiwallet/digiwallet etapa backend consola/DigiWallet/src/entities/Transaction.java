package entities;

import java.math.BigDecimal;
import java.util.Date;

public class Transaction {
	
	private int tr_id;
	private String tr_number; 
	private Account tr_sender_account_id; 
	private Account tr_receiver_account_id; 
	private BigDecimal tr_amount_sender; 
	private BigDecimal tr_amount_receiver;
	private Date tr_date;
	private String tr_detail; 
	private boolean tr_state; 
	
	public Transaction() {}

	public Transaction(int tr_id, String tr_number, Account tr_sender_account_id, Account tr_receiver_account_id,
			BigDecimal tr_amount_sender, BigDecimal tr_amount_receiver, Date tr_date, String tr_detail, boolean tr_state) {
		super();
		this.tr_id = tr_id;
		this.tr_number = tr_number;
		this.tr_sender_account_id = tr_sender_account_id;
		this.tr_receiver_account_id = tr_receiver_account_id;
		this.tr_amount_sender = tr_amount_sender;
		this.tr_amount_receiver = tr_amount_receiver;
		this.tr_date = tr_date;
		this.tr_detail = tr_detail;
		this.tr_state = tr_state;
	}

	public int getTr_id() {
		return tr_id;
	}

	public void setTr_id(int tr_id) {
		this.tr_id = tr_id;
	}

	public String getTr_number() {
		return tr_number;
	}

	public void setTr_number(String tr_number) {
		this.tr_number = tr_number;
	}

	public Account getTr_sender_account_id() {
		return tr_sender_account_id;
	}

	public void setTr_sender_account_id(Account tr_sender_account_id) {
		this.tr_sender_account_id = tr_sender_account_id;
	}

	public Account getTr_receiver_account_id() {
		return tr_receiver_account_id;
	}

	public void setTr_receiver_account_id(Account tr_receiver_account_id) {
		this.tr_receiver_account_id = tr_receiver_account_id;
	}

	public BigDecimal getTr_amount_sender() {
		return tr_amount_sender;
	}

	public void setTr_amount_sender(BigDecimal tr_amount_sender) {
		this.tr_amount_sender = tr_amount_sender;
	}

	public BigDecimal getTr_amount_receiver() {
		return tr_amount_receiver;
	}

	public void setTr_amount_receiver(BigDecimal tr_amount_receiver) {
		this.tr_amount_receiver = tr_amount_receiver;
	}

	public Date getTr_date() {
		return tr_date;
	}

	public void setTr_date(Date tr_date) {
		this.tr_date = tr_date;
	}

	public String getTr_detail() {
		return tr_detail;
	}

	public void setTr_detail(String tr_detail) {
		this.tr_detail = tr_detail;
	}

	public boolean isTr_state() {
		return tr_state;
	}

	public void setTr_state(boolean tr_state) {
		this.tr_state = tr_state;
	}

}
