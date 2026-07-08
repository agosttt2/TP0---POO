package juego.modelo;

public abstract class Personaje {

    private final String nombre;
    private int vida;
    private final int vidaMaxima;
    private int ataqueBase;
    private int defensa;
    private final int velocidad;
    private int nivel;
    private int experiencia;
    private double multiplicadorArma;
    private EstadoPersonaje estado;

    protected Personaje(String nombre, int vida, int ataqueBase, int defensa, int velocidad, double multiplicadorArma) {
        this.nombre = nombre;
        this.vida = vida;
        this.vidaMaxima = vida;
        this.ataqueBase = ataqueBase;
        this.defensa = defensa;
        this.velocidad = velocidad;
        this.multiplicadorArma = multiplicadorArma;
        this.nivel = 1;
        this.experiencia = 0;
        this.estado = EstadoPersonaje.VIVO;
    }

    public int calcularDanoFinal() {
        return (int) (ataqueBase * multiplicadorArma);
    }

    public void recibirAtaque(int danioBase) {
        int reduccion = defensa + (estaDefendiendo() ? defensa : 0);
        int danioFinal = Math.max(1, danioBase - reduccion);
        aplicarDanio(danioFinal);
        if (estaDefendiendo()) {
            dejarDeDefender();
        }
    }

    private void aplicarDanio(int danio) {
        vida -= danio;
        if (vida < 0) {
            vida = 0;
        }
        if (vida == 0) {
            estado = EstadoPersonaje.MUERTO;
        }
    }

    public void curar(int cantidad) {
        vida += cantidad;
        if (vida > vidaMaxima) {
            vida = vidaMaxima;
        }
        if (vida > 0 && estado == EstadoPersonaje.MUERTO) {
            estado = EstadoPersonaje.VIVO;
        }
    }

    public void defender() {
        if (estaVivo()) {
            estado = EstadoPersonaje.DEFENDIENDO;
        }
    }

    public void dejarDeDefender() {
        if (estado == EstadoPersonaje.DEFENDIENDO) {
            estado = EstadoPersonaje.VIVO;
        }
    }

    public boolean estaDefendiendo() {
        return estado == EstadoPersonaje.DEFENDIENDO;
    }

    public boolean estaVivo() {
        return vida > 0;
    }

    /**
     * @return true si con esta experiencia subió (uno o más) niveles.
     */
    public boolean ganarExperiencia(int xp) {
        experiencia += xp;
        boolean subioNivel = false;
        while (experiencia >= getExperienciaNecesaria()) {
            experiencia -= getExperienciaNecesaria();
            nivel++;
            subioNivel = true;
        }
        return subioNivel;
    }

    public int getExperienciaNecesaria() {
        return nivel * 100;
    }

    public void aumentarAtaque(int cantidad) {
        ataqueBase += cantidad;
    }

    public void aumentarDefensa(int cantidad) {
        defensa += cantidad;
    }

    public void mejorarArma(double aumento) {
        multiplicadorArma += aumento;
    }

    public void restaurarEstado(int vida, EstadoPersonaje estado, int nivel, int experiencia) {
        this.vida = Math.min(vida, vidaMaxima);
        this.estado = estado;
        this.nivel = Math.max(1, nivel);
        this.experiencia = Math.max(0, experiencia);
    }

    public String getNombre() { return nombre; }
    public int getVida() { return vida; }
    public int getVidaMaxima() { return vidaMaxima; }
    public int getAtaqueBase() { return ataqueBase; }
    public int getDefensa() { return defensa; }
    public int getVelocidad() { return velocidad; }
    public int getNivel() { return nivel; }
    public int getExperiencia() { return experiencia; }
    public double getMultiplicadorArma() { return multiplicadorArma; }
    public EstadoPersonaje getEstado() { return estado; }

    public abstract String[] getNombresHabilidades();
    public abstract String getSpriteHabilidad(int index);

    public abstract int ejecutarHabilidad(int index);

    public boolean habilidadEsCuracion(int index) {
        return false;
    }

    public int usarHabilidad() {
        return ejecutarHabilidad(0);
    }
}
