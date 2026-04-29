package ar.edu.utn.ba.ddsi.smartlife.trends_service.models.entities.tendencia;

public final class Icono {

	public static final Icono CHART = new Icono(new int[]{0x1F4CA});
	public static final Icono ROCKET = new Icono(new int[]{0x1F680});
	public static final Icono FIRE = new Icono(new int[]{0x1F525});

	private final int[] internalEncoding;

	private Icono(int[] internalEncoding) {
		this.internalEncoding = internalEncoding;
	}

	public String texto() {
		return new String(internalEncoding, 0, internalEncoding.length);
	}
}
