package padroescomportamentais.state;

public final class ReprodutorEstadoReproduzindo extends ReprodutorEstado {
    private static final ReprodutorEstadoReproduzindo instance = new ReprodutorEstadoReproduzindo();

    private ReprodutorEstadoReproduzindo() {
    }

    public static ReprodutorEstadoReproduzindo getInstance() {
        return instance;
    }

    @Override
    public String getEstado() {
        return "Reproduzindo";
    }

    @Override
    public boolean pausar(Reprodutor reprodutor) {
        reprodutor.setEstado(ReprodutorEstadoPausado.getInstance());
        return true;
    }

    @Override
    public boolean parar(Reprodutor reprodutor) {
        reprodutor.setEstado(ReprodutorEstadoParado.getInstance());
        return true;
    }
}
