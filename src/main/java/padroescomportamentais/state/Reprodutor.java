package padroescomportamentais.state;

import java.util.Objects;

public class Reprodutor {
    private ReprodutorEstado estado;

    public Reprodutor() {
        this.estado = ReprodutorEstadoParado.getInstance();
    }

    public ReprodutorEstado getEstado() {
        return estado;
    }

    // As classes do pacote controlam as transições.
    void setEstado(ReprodutorEstado estado) {
        this.estado = Objects.requireNonNull(estado, "Estado obrigatório");
    }

    public String getNomeEstado() {
        return estado.getEstado();
    }

    public boolean reproduzir() {
        return estado.reproduzir(this);
    }

    public boolean pausar() {
        return estado.pausar(this);
    }

    public boolean parar() {
        return estado.parar(this);
    }
}
