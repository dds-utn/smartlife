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
	private long ventasAcumuladas;
	private long likes;
	private long dislikes;
	private LocalDateTime fechaUltimaVenta;
	private boolean promocionBloqueadaPorDislikes;
	private EstadoTendencia estado;

	public Producto() {
		this.estado = new Normal();
	}

	public void registrarVenta(long cantidad) {
		this.promocionBloqueadaPorDislikes = false;
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

	public String iconoTexto() {
		return estado.iconoTexto();
	}

	public String leyenda() {
		return estado.leyenda(this);
	}

	public String detalle() {
		return estado.detalle(this);
	}

	public String etiqueta() {
		return estado.etiqueta();
	}
}
