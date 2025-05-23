public class Resultado {
    public String estado; // "Ganó" o "Perdió"
    public int tiempo;
    public int intentos;

    public Resultado(String estado, int tiempo, int intentos) {
        this.estado = estado;
        this.tiempo = tiempo;
        this.intentos = intentos;
    }

    @Override
    public String toString() {
        return String.format("%s - Tiempo: %ds, Intentos: %d", estado, tiempo, intentos);
    }
}
