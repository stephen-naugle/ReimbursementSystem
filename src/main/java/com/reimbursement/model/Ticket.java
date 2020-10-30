package com.reimbursement.model;

import java.time.LocalDateTime;


public class Ticket {

	private int reimbId;
	private int authorId;
	private int resolverId;
	private double amount;
	private LocalDateTime timeSubmitted;    //Changed from String to LocalDateTime
	private LocalDateTime timeResolved;	   // Changed from String to LocalDateTime
	private String ticketDescription;
	private ReimbursementStatusEnum status;
	private ReimbursementTypeEnum type;
	
	
	public Ticket() {
		
	}

	/**
	 * constructor for when retrieving a Ticket from the DB
	 * @param reimbId
	 * @param authorId
	 * @param resolverId
	 * @param amount
	 * @param timeSubmitted
	 * @param timeResolved
	 * @param ticketDescription
	 * @param status
	 * @param type
	 */
	public Ticket(int reimbId, int authorId, int resolverId, double amount, LocalDateTime timeSubmitted, LocalDateTime timeResolved,
			String ticketDescription, ReimbursementStatusEnum status, ReimbursementTypeEnum type) {
		super();
		this.reimbId = reimbId;
		this.authorId = authorId;
		this.resolverId = resolverId;
		this.amount = amount;
		this.timeSubmitted = timeSubmitted;                   // Changed to LocalDateTime ^
		this.timeResolved = timeResolved;					 // Changed to LocalDateTime ^
		this.ticketDescription = ticketDescription;
		this.status = status;
		this.type = type;
	}

	/**
	 * user does not choose who the resolver is, nor do they choose the status of their ticket upon creation, this is done by the DB, which sets 		it to PENDING by default upon persistence
	 * @param authorId
	 * @param amount
	 * @param ticketDescription
	 * @param type
	 */
	public Ticket(int authorId, double amount, String ticketDescription, ReimbursementTypeEnum type) {
		super();
		this.authorId = authorId;
		this.amount = amount;
		this.ticketDescription = ticketDescription;
		this.status = ReimbursementStatusEnum.PENDING;
		this.type = type;
	}

	
	public int getStatusId() {
		switch(this.getStatus()) {
		case PENDING:
			return 0;
		case APPROVED:
			return 1;
		case DENIED:
			return 2;
		}
		
		return 0;
	}
	
	
	// Getters and Setters --------------------------------------------------------------------------------------
	
	public int getReimbId() {
		return reimbId;
	}

	public void setReimbId(int reimbId) {
		this.reimbId = reimbId;
	}

	public int getAuthorId() {
		return authorId;
	}

	public void setAuthorId(int authorId) {
		this.authorId = authorId;
	}

	public int getResolverId() {
		return resolverId;
	}

	public void setResolverId(int resolverId) {
		this.resolverId = resolverId;
	}

	public double getAmount() {
		return amount;
	}

	public void setAmount(double amount) {
		this.amount = amount;
	}

	public LocalDateTime getTimeSubmitted() {                                       // Changed to LocalDateTime
		return timeSubmitted;
	}

	public void setTimeSubmitted(LocalDateTime string) {                    // Changed to LocalDateTime
		this.timeSubmitted = string;
	}

	public LocalDateTime getTimeResolved() {                                     // Changed to LocalDateTime
		return timeResolved;
	}

	public void setTimeResolved(LocalDateTime localDateTime) {                   // Changed to LocalDateTime
		this.timeResolved = localDateTime;
	}

	public String getTicketDescription() {
		return ticketDescription;
	}

	public void setTicketDescription(String ticketDescription) {
		this.ticketDescription = ticketDescription;
	}

	public ReimbursementStatusEnum getStatus() {
		return status;
	}

	public void setStatus(ReimbursementStatusEnum status) {
		this.status = status;
	}

	public ReimbursementTypeEnum getType() {
		return type;
	}

	public void setType(ReimbursementTypeEnum type) {
		this.type = type;
	}

	
	// hashCode(), equals(), and toString() --------------------------------------------------------------------
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		long temp;
		temp = Double.doubleToLongBits(amount);
		result = prime * result + (int) (temp ^ (temp >>> 32));
		result = prime * result + authorId;
		result = prime * result + reimbId;
		result = prime * result + resolverId;
		result = prime * result + ((status == null) ? 0 : status.hashCode());
		result = prime * result + ((ticketDescription == null) ? 0 : ticketDescription.hashCode());
		result = prime * result + ((timeResolved == null) ? 0 : timeResolved.hashCode());
		result = prime * result + ((timeSubmitted == null) ? 0 : timeSubmitted.hashCode());
		result = prime * result + ((type == null) ? 0 : type.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Ticket other = (Ticket) obj;
		if (Double.doubleToLongBits(amount) != Double.doubleToLongBits(other.amount))
			return false;
		if (authorId != other.authorId)
			return false;
		if (reimbId != other.reimbId)
			return false;
		if (resolverId != other.resolverId)
			return false;
		if (status != other.status)
			return false;
		if (ticketDescription == null) {
			if (other.ticketDescription != null)
				return false;
		} else if (!ticketDescription.equals(other.ticketDescription))
			return false;
		if (timeResolved == null) {
			if (other.timeResolved != null)
				return false;
		} else if (!timeResolved.equals(other.timeResolved))
			return false;
		if (timeSubmitted == null) {
			if (other.timeSubmitted != null)
				return false;
		} else if (!timeSubmitted.equals(other.timeSubmitted))
			return false;
		if (type != other.type)
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "Ticket [reimbId=" + reimbId + ", authorId=" + authorId + ", resolverId=" + resolverId + ", amount="
				+ amount + ", timeSubmitted=" + timeSubmitted + ", timeResolved=" + timeResolved
				+ ", ticketDescription=" + ticketDescription + ", status=" + status + ", type=" + type + "]";
	}
	
	
	
}


