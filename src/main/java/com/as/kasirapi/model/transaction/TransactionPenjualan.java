package com.as.kasirapi.model.transaction;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import com.as.kasirapi.model.BaseEntity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@Builder
@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "trx_penjualan", schema = "transaction")
public class TransactionPenjualan extends BaseEntity {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long   id;
	@Column(name = "invoice_number")
	private String invoiceNumber;
	@Column(name = "product_detail_id")
	private Long   productDetailId;
	private int    qty;
	private Double total;
	@Column(name = "member_id")
	private Long   memberId;

	@Override
	public String toString() {
		return "invoiceNumber=" + invoiceNumber + ", productDetailId=" + productDetailId + ", total=" + total;
	}
}
