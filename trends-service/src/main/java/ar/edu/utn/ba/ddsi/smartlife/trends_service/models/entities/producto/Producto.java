package ar.edu.utn.ba.ddsi.smartlife.trends_service.models.entities.producto;

import ar.edu.utn.ba.ddsi.smartlife.trends_service.models.entities.comercio.Comercio;
import ar.edu.utn.ba.ddsi.smartlife.trends_service.models.entities.tendencia.EstadoTendencia;
import ar.edu.utn.ba.ddsi.smartlife.trends_service.models.entities.tendencia.Normal;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class Producto {

	private Long id;
	private Comercio comercio;
	private String nombre;
	private String categoria;
	private double precioBase;
	private int ventasAcumuladas;
	private int likes;
	private int dislikes;
	private LocalDateTime fechaUltimaVenta;
	private EstadoTendencia estado;

	public Producto() {
		this.estado = new Normal();
	}

	public void registrarVenta(int cantidad) {
		this.ventasAcumuladas += cantidad;
		this.fechaUltimaVenta = LocalDateTime.now();
		this.estado.nuevaVentaDe(this);
	}

	public void recibirLike() {
		this.likes++;
		this.estado.likePara(this);
	}

	public void recibirDislike() {
		this.dislikes++;
		this.estado.dislikePara(this);
	}

	public String detalle() {
		return estado.detalle(this);
	}
}
