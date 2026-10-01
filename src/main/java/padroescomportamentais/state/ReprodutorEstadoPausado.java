package padroescomportamentais.state;

public final class ReprodutorEstadoPausado extends ReprodutorEstado {
    private static final ReprodutorEstadoPausado instance = new ReprodutorEstadoPausado();

    private ReprodutorEstadoPausado() {
    }

    public static ReprodutorEstadoPausado getInstance() {
        return instance;
    }

    @Override
    public String getEstado() {
        return "Pausado";
    }

    @Override
    public boolean reproduzir(Reprodutor reprodutor) {
        reprodutor.setEstado(ReprodutorEstadoReproduzindo.getInstance());
        return true;
    }

    @Override
    public boolean parar(Reprodutor reprodutor) {
        reprodutor.setEstado(ReprodutorEstadoParado.getInstance());
        return true;
    }
}
