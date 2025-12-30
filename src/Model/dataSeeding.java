package Model; // Ou qualquer pacote que você preferir

import Model.SystemSIGEE;
import java.time.LocalDate;
import java.time.LocalDateTime;

/**
 * Classe utilitária para "semear" (popular) o sistema
 * com dados de exemplo para fins de teste da GUI.
 * * Esta classe não pode ser instanciada.
 */
public final class dataSeeding {

    /**
     * Construtor privado para impedir a instanciação.
     */
    private dataSeeding() {}

    /**
     * Preenche o singleton SystemSIGEE com dados de exemplo.
     */
    public static void popularDados() {
        // Pega a instância única do nosso sistema
        SystemSIGEE sistema = SystemSIGEE.getInstance();

        // --- 1. Criar Pessoas ---
        sistema.registerUndergraduateStudent("Ana Silva", "ana.silva@ufsc.com", "20241001");
        sistema.registerUndergraduateStudent("Bruno Costa", "bruno.costa@ufsc.com", "20241002");
        sistema.registerUndergraduateStudent("Carla Dias", "carla.dias@ufsc.com", "20241003");
        sistema.registerProfessor("Dr. Eduardo", "eduardo.prof@ufsc.com", "98765432");

        // --- 2. Criar Cargos (Roles) ---
        sistema.createRole("Presidente", "Líder da entidade", 1200.00);
        sistema.createRole("Tesoureiro", "Cuida das finanças", 800.00);
        sistema.createRole("Membro", "Participa das atividades", 0.00);

        // --- 3. Criar Entidades ---
        sistema.createEntity("PET Informática", "Grupo de educação tutorial");
        sistema.createEntity("Atlética de TI", "Associação atlética");
        sistema.createEntity("CA de Computação", "Centro Acadêmico");

        // --- 4. Criar Eventos (em uma entidade) ---
        sistema.createEvent("PET Informática", "Semana da Computação", "Auditório LCC", LocalDateTime.now().plusDays(10));
        sistema.createEvent("PET Informática", "Hackathon", "Lab. 302", LocalDateTime.now().plusDays(20));
        sistema.createEvent("Atlética de TI", "Intercomp", "Quadra de Esportes", LocalDateTime.now().plusDays(30));

        // --- 5. Criar Vínculos (Bonds) ---
        // Ana é Presidente do PET
        sistema.createBond("20241001", "PET Informática", "Presidente");
        // Bruno é Membro do PET
        sistema.createBond("20241002", "PET Informática", "Membro");
        // Dr. Eduardo é Tesoureiro da Atlética
        sistema.createBond("98765432", "Atlética de TI", "Tesoureiro");
        // Carla é Membro do CA
        sistema.createBond("20241003", "CA de Computação", "Membro");

        // --- 6. Adicionar Participantes em Eventos ---
        sistema.addParticipantToEvent("20241001", "Semana da Computação"); // Ana
        sistema.addParticipantToEvent("20241002", "Semana da Computação"); // Bruno
        sistema.addOrganizerToEvent("98765432", "Intercomp"); // Dr. Eduardo
    }
}
