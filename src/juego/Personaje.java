package juego;

public abstract class Personaje {

    protected String nombre;
    protected String estado;
    protected int vida;
    protected int vidaMaxima;
    protected int ataqueBase;
    protected int defensa;
    protected int velocidad;
    protected int nivel;
    protected int experiencia;
    protected double multiplicadorArma;

    public Personaje(String nombre, int vida, int ataqueBase, int defensa, int velocidad, double multiplicadorArma) {
        this.nombre           = nombre;
        this.vida             = vida;
        this.vidaMaxima       = vida;
        this.ataqueBase       = ataqueBase;
        this.defensa          = defensa;
        this.velocidad        = velocidad;
        this.multiplicadorArma = multiplicadorArma;
        this.nivel            = 1;
        this.experiencia      = 0;
        this.estado           = "VIVO";
    }

<<<<<<< HEAD
   
=======
>>>>>>> d7c497ce7ccda5f8c912299555ea3c3c07d75d65
    public int calcularDanoFinal() {
        return (int)(ataqueBase * multiplicadorArma);
    }

    public String getNombre()     { return nombre; }
    public int getVida()          { return vida; }
    public int getVidaMaxima()    { return vidaMaxima; }
    public int getDefensa()       { return defensa; }
    public int getVelocidad()     { return velocidad; }
    public int getNivel()         { return nivel; }
    public int getExperiencia()   { return experiencia; }
    public String getEstado()     { return estado; }
    public int getAtaqueBase() {
        return ataqueBase;
    }

    public double getMultiplicadorArma() {
        return multiplicadorArma;
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

    public int getExperienciaNecesaria() {
        return nivel * 100;
    }

    public void recibirDanio(int danio) {
        vida -= Math.max(1, danio - defensa);
        if (vida < 0) {
            vida = 0;
        }
        if (vida == 0) {
            estado = "MUERTO";
        }
    }

    public void curar(int cantidad) {
        vida += cantidad;
        if (vida > vidaMaxima) {
            vida = vidaMaxima;
        }
        if (vida > 0) {
            estado = "VIVO";
        }
    }

    public void ganarExperiencia(int xp) {
        experiencia += xp;
        if (experiencia >= getExperienciaNecesaria()) {
            experiencia -= getExperienciaNecesaria();
            nivel++;
        }
    }

    public boolean estaVivo() {
        return vida > 0;
    }

<<<<<<< HEAD

=======
>>>>>>> d7c497ce7ccda5f8c912299555ea3c3c07d75d65
    public abstract String[] getNombresHabilidades();
    public abstract String getSpriteHabilidad(int index);
    public abstract int ejecutarHabilidad(int index);

<<<<<<< HEAD

=======
>>>>>>> d7c497ce7ccda5f8c912299555ea3c3c07d75d65
    public boolean habilidadEsCuracion(int index) {
        return false;
    }

<<<<<<< HEAD
    
=======
>>>>>>> d7c497ce7ccda5f8c912299555ea3c3c07d75d65
    public abstract int usarHabilidad();
}