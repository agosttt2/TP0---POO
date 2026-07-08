package juego.modelo;


public abstract class Personaje {

    protected String nombre;
    public String estado;
    protected int vida;
    protected int vidaMaxima;
    protected int ataqueBase;
    protected int defensa;
    protected int velocidad;
    protected int nivel;
    protected int experiencia;
    protected double multiplicadorArma;

    private int ataqueBaseOriginal;
    private int defensaBaseOriginal;

    private static final int NIVEL_MAXIMO = 3;

    public Personaje(String nombre, int vida, int ataqueBase, int defensa, int velocidad, double multiplicadorArma) {
        this.nombre              = nombre;
        this.vida                = vida;
        this.vidaMaxima          = vida;
        this.ataqueBase          = ataqueBase;
        this.ataqueBaseOriginal  = ataqueBase;
        this.defensa             = defensa;
        this.defensaBaseOriginal = defensa;
        this.velocidad           = velocidad;
        this.multiplicadorArma   = multiplicadorArma;
        this.nivel               = 1;
        this.experiencia         = 0;
        this.estado              = "VIVO";
    }

    public int calcularDanoFinal() {
        return (int)(ataqueBase * multiplicadorArma);
    }

    public String getNombre()            { return nombre; }
    public int    getVida()              { return vida; }
    public int    getVidaMaxima()        { return vidaMaxima; }
    public int    getDefensa()           { return defensa; }
    public int    getVelocidad()         { return velocidad; }
    public int    getNivel()             { return nivel; }
    public int    getExperiencia()       { return experiencia; }
    public String getEstado()            { return estado; }
    public int    getAtaqueBase()        { return ataqueBase; }
    public double getMultiplicadorArma() { return multiplicadorArma; }

    public int getExperienciaNecesaria() {
        return nivel * 100;
    }

    public void aumentarAtaque(int cantidad) {
        ataqueBase          += cantidad;
        ataqueBaseOriginal  += cantidad;
    }

    public void aumentarDefensa(int cantidad) {
        defensa              += cantidad;
        defensaBaseOriginal  += cantidad;
    }

    public void mejorarArma(double aumento) {
        multiplicadorArma += aumento;
    }

    public void recibirAtaque(int danio) {
        vida -= Math.max(1, danio - defensa);
        if (vida < 0) vida = 0;
        if (vida == 0) estado = "MUERTO";
    }

    public void curar(int cantidad) {
        vida += cantidad;
        if (vida > vidaMaxima) vida = vidaMaxima;
        if (vida > 0 && estado.equals("MUERTO")) estado = "VIVO";
    }

    public boolean estaVivo() {
        return vida > 0;
    }

    public boolean ganarExperiencia(int xp) {
        if (nivel >= NIVEL_MAXIMO) return false;

        experiencia += xp;

        if (experiencia >= getExperienciaNecesaria()) {
            experiencia -= getExperienciaNecesaria();
            nivel++;
            aplicarBonusNivel();
            return true;
        }
        return false;
    }

    private void aplicarBonusNivel() {
        if (nivel == 2) {
            ataqueBase = (int)(ataqueBaseOriginal * 1.5);
            defensa    = (int)(defensaBaseOriginal * 1.5);
        }
        if (nivel == 3) {
            ataqueBase = (int)(ataqueBaseOriginal * 2.5);
            defensa    = (int)(defensaBaseOriginal * 2.5);
        }
    }

    public void restaurarEstado(int vida, int nivel, int experiencia) {
        this.vida        = vida;
        this.nivel       = nivel;
        this.experiencia = experiencia;
        this.estado      = vida > 0 ? "VIVO" : "MUERTO";
        aplicarBonusNivel();
    }

    public abstract String[] getNombresHabilidades();
    public abstract String   getSpriteHabilidad(int index);
    public abstract int      ejecutarHabilidad(int index);
    public abstract void     regenerarMana();
    public abstract int      getMana();

    public boolean habilidadEsCuracion(int index) {
        return false;
    }
}