package controle;

import dao.PaisDAO;
import modelo.Pais;

import java.util.List;

public class PaisController {

    private final PaisDAO paisDAO;
    private final ComparadorSuperTrunfo comparador;

    public PaisController(PaisDAO paisDAO) {
        this.paisDAO = paisDAO;
        this.comparador = new ComparadorSuperTrunfo();
    }

    public Pais cadastrarPais(String nome, String capital, long populacao, double area, double pib, int pontosTuristicos) {
        validar(nome, capital, populacao, area, pib, pontosTuristicos);
        Pais pais = new Pais(nome, capital, populacao, area, pib, pontosTuristicos);
        paisDAO.inserir(pais);
        return pais;
    }

    public void atualizarPais(int id, String nome, String capital, long populacao, double area, double pib, int pontosTuristicos) {
        validar(nome, capital, populacao, area, pib, pontosTuristicos);
        Pais existente = paisDAO.buscarPorId(id);
        if (existente == null) {
            throw new IllegalArgumentException("País não encontrado para o ID informado.");
        }

        existente.setNome(nome);
        existente.setCapital(capital);
        existente.setPopulacao(populacao);
        existente.setArea(area);
        existente.setPib(pib);
        existente.setPontosTuristicos(pontosTuristicos);
        paisDAO.atualizar(existente);
    }

    public void excluirPais(int id) {
        Pais existente = paisDAO.buscarPorId(id);
        if (existente == null) {
            throw new IllegalArgumentException("País não encontrado para exclusão.");
        }
        paisDAO.deletar(id);
    }

    public Pais buscarPais(int id) {
        return paisDAO.buscarPorId(id);
    }

    public List<Pais> listarPaises() {
        return paisDAO.listarTodos();
    }

    public String compararPaises(int id1, int id2, String atributo) {
        Pais pais1 = paisDAO.buscarPorId(id1);
        Pais pais2 = paisDAO.buscarPorId(id2);

        if (pais1 == null || pais2 == null) {
            throw new IllegalArgumentException("Um ou ambos os países não foram encontrados.");
        }
        return comparador.comparar(pais1, pais2, atributo);
    }

    public void carregarDadosIniciais() {
        if (!paisDAO.existeDados()) {
            paisDAO.inserir(new Pais("Brasil", "Brasília", 203062512L, 8515767.0, 2.17, 25));
            paisDAO.inserir(new Pais("Estados Unidos", "Washington, D.C.", 340110988L, 9833517.0, 27.72, 30));
            paisDAO.inserir(new Pais("Japão", "Tóquio", 123790000L, 377975.0, 4.21, 20));
            paisDAO.inserir(new Pais("França", "Paris", 68373433L, 551695.0, 3.05, 18));
            paisDAO.inserir(new Pais("Canadá", "Ottawa", 40126965L, 9984670.0, 2.14, 22));
        }
    }

    private void validar(String nome, String capital, long populacao, double area, double pib, int pontosTuristicos) {
        if (nome == null || nome.isBlank()) {
            throw new IllegalArgumentException("Nome do país é obrigatório.");
        }
        if (capital == null || capital.isBlank()) {
            throw new IllegalArgumentException("Capital é obrigatória.");
        }
        if (populacao <= 0 || area <= 0 || pib <= 0 || pontosTuristicos < 0) {
            throw new IllegalArgumentException("Os valores numéricos devem ser válidos e maiores que zero.");
        }
    }
}
