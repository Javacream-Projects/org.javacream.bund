package org.javacream.training.bund.dataaccess;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;

import lombok.Data;

@Data
@Entity
public class SimpleData implements Serializable{

	private static final long serialVersionUID = 1L;
	@Id
	//@GeneratedValue(strategy=GenerationType.AUTO)
	@Column(name="DATAID")
	private Long id = System.currentTimeMillis();
	@Column(name="MESSAGE")
	private String message;
}
