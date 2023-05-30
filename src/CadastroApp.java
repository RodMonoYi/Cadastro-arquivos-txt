import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class CadastroApp extends JFrame {
    private JButton btnCadastrarAluno, btnCadastrarProfessor, btnCadastrarDisciplina;
    private JButton btnExibirAlunos, btnExibirProfessores, btnExibirDisciplinas;
    private JTextArea txtAreaCadastros;
    private List<Cadastro> cadastros;
    private List<Disciplina> disciplinas;
    private String filePath = "C:\\Users\\Rodrigo\\Documents\\DadosCadastro\\arquivo.txt"; // Insira o caminho do arquivo aqui

    public CadastroApp() {
        setTitle("Programa de Cadastro");
        setSize(620, 280);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        getContentPane().setLayout(new BorderLayout());

        cadastros = new ArrayList<>();
        disciplinas = new ArrayList<>();

        JPanel panelBotoesCadastro = new JPanel();

        btnCadastrarAluno = new JButton("Cadastrar Aluno");
        btnCadastrarProfessor = new JButton("Cadastrar Professor");
        btnCadastrarDisciplina = new JButton("Cadastrar Disciplina");

        btnCadastrarAluno.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                abrirTelaCadastroAluno();
            }
        });

        btnCadastrarProfessor.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                abrirTelaCadastroProfessor();
            }
        });

        btnCadastrarDisciplina.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                abrirTelaCadastroDisciplina();
            }
        });
        panelBotoesCadastro.setLayout(new GridLayout(0, 1, 0, 0));

        panelBotoesCadastro.add(btnCadastrarAluno);
        panelBotoesCadastro.add(btnCadastrarProfessor);
        panelBotoesCadastro.add(btnCadastrarDisciplina);

        JPanel panelBotoesExibicao = new JPanel();

        btnExibirAlunos = new JButton("Exibir Alunos");
        btnExibirProfessores = new JButton("Exibir Professores");
        btnExibirDisciplinas = new JButton("Exibir Disciplinas");

        btnExibirAlunos.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                exibirCadastros("Aluno");
            }
        });

        btnExibirProfessores.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                exibirCadastros("Professor");
            }
        });

        btnExibirDisciplinas.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                exibirCadastros("Disciplina");
            }
        });
        panelBotoesExibicao.setLayout(new GridLayout(0, 1, 0, 0));

        panelBotoesExibicao.add(btnExibirAlunos);
        panelBotoesExibicao.add(btnExibirProfessores);
        panelBotoesExibicao.add(btnExibirDisciplinas);

        GridLayout gl_panelBotoes = new GridLayout(2, 1);
        gl_panelBotoes.setVgap(20);
        JPanel panelBotoes = new JPanel(gl_panelBotoes);
        panelBotoes.add(panelBotoesCadastro);
        panelBotoes.add(panelBotoesExibicao);

        txtAreaCadastros = new JTextArea();
        txtAreaCadastros.setEditable(false);

        JScrollPane scrollPane = new JScrollPane(txtAreaCadastros);
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);

        JPanel panelPrincipal = new JPanel(new BorderLayout());
        panelPrincipal.add(panelBotoes, BorderLayout.WEST);
        panelPrincipal.add(scrollPane, BorderLayout.CENTER);

        getContentPane().add(panelPrincipal);

        // Carrega os dados do arquivo ao iniciar o aplicativo
        carregarDados();

        setVisible(true);
    }

    private void abrirTelaCadastroAluno() {
        JFrame frame = new JFrame("Cadastro de Aluno");
        frame.setSize(300, 200);
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.getContentPane().setLayout(new GridLayout(3, 2));

        JLabel lblNome = new JLabel("Nome:");
        JLabel lblCurso = new JLabel("Curso:");

        JTextField txtNome = new JTextField();
        JTextField txtCurso = new JTextField();

        JButton btnSalvar = new JButton("Salvar");
        btnSalvar.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                String id = gerarId("Aluno");
                String nome = txtNome.getText();
                String curso = txtCurso.getText();

                if (!nome.isEmpty() && !curso.isEmpty()) {
                    Aluno aluno = new Aluno(id, nome, curso);
                    cadastros.add(aluno);
                    exibirCadastros("Aluno");
                    frame.dispose();
                    salvarDados(); // Salva os dados após cadastrar um aluno
                } else {
                    JOptionPane.showMessageDialog(frame, "Por favor, preencha todos os campos.");
                }
            }
        });

        JButton btnCancelar = new JButton("Cancelar");
        btnCancelar.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                frame.dispose();
            }
        });

        frame.getContentPane().add(lblNome);
        frame.getContentPane().add(txtNome);
        frame.getContentPane().add(lblCurso);
        frame.getContentPane().add(txtCurso);
        frame.getContentPane().add(btnSalvar);
        frame.getContentPane().add(btnCancelar);

        frame.setVisible(true);
    }

    private void abrirTelaCadastroProfessor() {
        JFrame frame = new JFrame("Cadastro de Professor");
        frame.setSize(300, 200);
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.getContentPane().setLayout(new GridLayout(4, 2));

        JLabel lblNome = new JLabel("Nome:");
        JLabel lblDepartamento = new JLabel("Departamento:");
        JLabel lblDisciplina = new JLabel("Disciplina:");

        JTextField txtNome = new JTextField();
        JTextField txtDepartamento = new JTextField();
        JComboBox<String> comboDisciplinas = new JComboBox<>();

        for (Disciplina disciplina : disciplinas) {
            comboDisciplinas.addItem(disciplina.getNome());
        }

        JButton btnSalvar = new JButton("Salvar");
        btnSalvar.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                String id = gerarId("Professor");
                String nome = txtNome.getText();
                String departamento = txtDepartamento.getText();
                String disciplina = (String) comboDisciplinas.getSelectedItem();

                if (!nome.isEmpty() && !departamento.isEmpty()) {
                    Professor professor = new Professor(id, nome, departamento, disciplina);
                    cadastros.add(professor);
                    exibirCadastros("Professor");
                    frame.dispose();
                    salvarDados(); // Salva os dados após cadastrar um professor
                } else {
                    JOptionPane.showMessageDialog(frame, "Por favor, preencha todos os campos.");
                }
            }
        });

        JButton btnCancelar = new JButton("Cancelar");
        btnCancelar.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                frame.dispose();
            }
        });

        frame.getContentPane().add(lblNome);
        frame.getContentPane().add(txtNome);
        frame.getContentPane().add(lblDepartamento);
        frame.getContentPane().add(txtDepartamento);
        frame.getContentPane().add(lblDisciplina);
        frame.getContentPane().add(comboDisciplinas);
        frame.getContentPane().add(btnSalvar);
        frame.getContentPane().add(btnCancelar);

        frame.setVisible(true);
    }

    private void abrirTelaCadastroDisciplina() {
        JFrame frame = new JFrame("Cadastro de Disciplina");
        frame.setSize(300, 200);
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.getContentPane().setLayout(new GridLayout(3, 2));

        JLabel lblNome = new JLabel("Nome:");
        JLabel lblDepartamento = new JLabel("Departamento:");

        JTextField txtNome = new JTextField();
        JTextField txtDepartamento = new JTextField();

        JButton btnSalvar = new JButton("Salvar");
        btnSalvar.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                String id = gerarId("Disciplina");
                String nome = txtNome.getText();
                String departamento = txtDepartamento.getText();

                if (!nome.isEmpty() && !departamento.isEmpty()) {
                    Disciplina disciplina = new Disciplina(id, nome, departamento);
                    disciplinas.add(disciplina);
                    cadastros.add(disciplina);
                    exibirCadastros("Disciplina");
                    frame.dispose();
                    salvarDados(); // Salva os dados após cadastrar uma disciplina
                } else {
                    JOptionPane.showMessageDialog(frame, "Por favor, preencha todos os campos.");
                }
            }
        });

        JButton btnCancelar = new JButton("Cancelar");
        btnCancelar.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                frame.dispose();
            }
        });

        frame.getContentPane().add(lblNome);
        frame.getContentPane().add(txtNome);
        frame.getContentPane().add(lblDepartamento);
        frame.getContentPane().add(txtDepartamento);
        frame.getContentPane().add(btnSalvar);
        frame.getContentPane().add(btnCancelar);

        frame.setVisible(true);
    }

    private String gerarId(String tipo) {
        int count = 1;
        for (Cadastro cadastro : cadastros) {
            if (cadastro.getTipo().equals(tipo)) {
                count++;
            }
        }
        return tipo.toLowerCase() + count;
    }

    private void exibirCadastros(String tipo) {
        StringBuilder sb = new StringBuilder();
        sb.append("----- Cadastros de ").append(tipo).append(" -----\n");

        for (Cadastro cadastro : cadastros) {
            if (cadastro.getTipo().equals(tipo)) {
                sb.append(cadastro.toString()).append("\n");
            }
        }

        txtAreaCadastros.setText(sb.toString());
    }

    private void carregarDados() {
        try {
            BufferedReader reader = new BufferedReader(new FileReader(filePath));
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(",");
                if (parts.length == 4) {
                    String tipo = parts[0];
                    String id = parts[1];
                    String nome = parts[2];
                    String extraData = parts[3];
                    if (tipo.equals("Aluno")) {
                        Aluno aluno = new Aluno(id, nome, extraData);
                        cadastros.add(aluno);
                    } else if (tipo.equals("Professor")) {
                        Professor professor = new Professor(id, nome, extraData, "");
                        cadastros.add(professor);
                    } else if (tipo.equals("Disciplina")) {
                        Disciplina disciplina = new Disciplina(id, nome, extraData);
                        disciplinas.add(disciplina);
                        cadastros.add(disciplina);
                    }
                }
            }
            reader.close();
            
            exibirCadastros("Aluno"); // Exibe os cadastros de Aluno
            exibirCadastros("Professor"); // Exibe os cadastros de Professor
            exibirCadastros("Disciplina"); // Exibe os cadastros de Disciplina
        } catch (IOException e) {
            System.out.println("Erro ao ler o arquivo.");
        }
    }


    private void salvarDados() {
        try {
            BufferedWriter writer = new BufferedWriter(new FileWriter(filePath, true)); // Abre o arquivo em modo de "append"
            Cadastro ultimoCadastro = cadastros.get(cadastros.size() - 1); // Obtém o último cadastro adicionado

            writer.write("Tipo de cadastro: " + ultimoCadastro.getTipo() + "\n");
            writer.write("ID: " + ultimoCadastro.getId() + "\n");
            writer.write("Nome: " + ultimoCadastro.getNome() + "\n");
            writer.write(ultimoCadastro.getExtraData() + "\n\n");

            writer.close();
            System.out.println("Dados salvos no arquivo: " + filePath);
        } catch (IOException e) {
            System.out.println("Erro ao salvar os dados no arquivo.");
        }
    }


    public static void main(String[] args) {
        new CadastroApp();
    }
}

class Cadastro {
    private String id;
    private String nome;

    public Cadastro(String id, String nome) {
        this.id = id;
        this.nome = nome;
    }

    public String getId() {
        return id;
    }

    public String getNome() {
        return nome;
    }

    public String getTipo() {
        return getClass().getSimpleName();
    }

    public String getExtraData() {
        return "";
    }

    @Override
    public String toString() {
        return "ID: " + id + " | Nome: " + nome;
    }
}

class Aluno extends Cadastro {
    private String curso;

    public Aluno(String id, String nome, String curso) {
        super(id, nome);
        this.curso = curso;
    }

    public String getExtraData() {
        return "Curso: " + curso;
    }

    @Override
    public String toString() {
        return super.toString() + " | Curso: " + curso;
    }
}

class Professor extends Cadastro {
    private String departamento;
    private String disciplina;

    public Professor(String id, String nome, String departamento, String disciplina) {
        super(id, nome);
        this.departamento = departamento;
        this.disciplina = disciplina;
    }

    public String getExtraData() {
        return "Departamento: " + departamento + " | Disciplina: " + disciplina;
    }

    @Override
    public String toString() {
        return super.toString() + " | Departamento: " + departamento + " | Disciplina: " + disciplina;
    }
}

class Disciplina extends Cadastro {
    private String departamento;

    public Disciplina(String id, String nome, String departamento) {
        super(id, nome);
        this.departamento = departamento;
    }

    public String getExtraData() {
        return "Departamento: " + departamento;
    }

    @Override
    public String toString() {
        return super.toString() + " | Departamento: " + departamento;
    }
}
