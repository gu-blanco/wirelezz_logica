
public class main {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		Jogador Novo;
		Novo = new Jogador("Gustavo",10);
		System.out.println(Novo.getNome() + " - " + Novo.getRecurso());
		Novo.subRecurso(5);
		System.out.println(Novo.getNome() + " - " + Novo.getRecurso());
		Novo.somaRecurso(2);
		System.out.println(Novo.getNome() + " - " + Novo.getRecurso());
	}
}
