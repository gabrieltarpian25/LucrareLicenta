package net.lucrare.licenta;

import java.util.Date;

import org.hibernate.annotations.NamedQueries;
import org.hibernate.annotations.NamedQuery;


import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;


@Entity
@NamedQueries({
	@NamedQuery(name="Factura.findAll",
				query="SELECT f FROM Factura f"),
})

public class Factura {
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private Long id;
	
	@Column(name="UTILITATE")
	private String utilitate;
	
	@Column(name="NR_FACTURA")
	private String nrFactura;
	
	@Column(name="COMPANIE")
	private String companie;
	
	@Column(name="DATA_EMITERII")
	private Date dataEmiterii;
	
	@Column(name="DATA_SCADENTA")
	private Date dataScadenta;
	
	@Column(name="TOTAL_PLATA")
	private float totalPlata;
	
	@Column(name="STATUS")
	private String status;
	
	@Column(name = "LINK")
	private String link;

	public String getNrFactura() {
		return nrFactura;
	}

	public void setNrFactura(String nrFactura) {
		this.nrFactura = nrFactura;
	}

	public String getCompanie() {
		return companie;
	}

	public void setCompanie(String companie) {
		this.companie = companie;
	}

	public Date getDataEmiterii() {
		return dataEmiterii;
	}

	public void setDataEmiterii(Date dataEmiterii) {
		this.dataEmiterii = dataEmiterii;
	}

	public Date getDataScadenta() {
		return dataScadenta;
	}

	public void setDataScadenta(Date dataScadenta) {
		this.dataScadenta = dataScadenta;
	}

	public float getTotalPlata() {
		return totalPlata;
	}

	public void setTotalPlata(float totalPlata) {
		this.totalPlata = totalPlata;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getLink() {
		return link;
	}

	public void setLink(String link) {
		this.link = link;
	}

	public String getUtilitate() {
		return utilitate;
	}

	public void setUtilitate(String utilitate) {
		this.utilitate = utilitate;
	}


}
