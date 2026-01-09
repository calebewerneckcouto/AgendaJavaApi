package com.agenda.br.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.agenda.br.model.dto.ContatoDTO;
import com.agenda.br.model.dto.ContatoRequest;
import com.agenda.br.model.dto.PageResponse;
import com.agenda.br.service.ContatoService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/contatos")
@Tag(name = "Agenda", description = "API para gerenciamento de contatos")
@CrossOrigin(origins = "*")
public class AgendaController {
    
    private final ContatoService contatoService;
    
    public AgendaController(ContatoService contatoService) {
        this.contatoService = contatoService;
    }
    
    @PostMapping
    @Operation(
        summary = "Criar um novo contato",
        description = "Adiciona um novo contato na agenda"
    )
    @ApiResponses(value = {
        @ApiResponse(responseCode = "201", description = "Contato criado com sucesso",
            content = @Content(schema = @Schema(implementation = ContatoDTO.class))),
        @ApiResponse(responseCode = "400", description = "Dados inválidos fornecidos"),
        @ApiResponse(responseCode = "409", description = "Email já cadastrado")
    })
    public ResponseEntity<ContatoDTO> criarContato(
            @Parameter(description = "Dados do contato a ser criado", required = true)
            @Valid @RequestBody ContatoRequest request) {
        ContatoDTO contatoSalvo = contatoService.salvar(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(contatoSalvo);
    }
    
    @GetMapping
    @Operation(
        summary = "Listar contatos paginados",
        description = "Retorna uma lista paginada de contatos com opções de ordenação"
    )
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Lista de contatos retornada com sucesso")
    })
    public ResponseEntity<PageResponse<ContatoDTO>> listarContatos(
            @Parameter(description = "Número da página (começa em 0)", example = "0")
            @RequestParam(defaultValue = "0") int pagina,
            
            @Parameter(description = "Quantidade de itens por página", example = "10")
            @RequestParam(defaultValue = "10") int tamanho,
            
            @Parameter(description = "Campo para ordenação", example = "nome")
            @RequestParam(defaultValue = "nome") String ordem,
            
            @Parameter(description = "Direção da ordenação (asc ou desc)", example = "asc")
            @RequestParam(defaultValue = "asc") String direcao) {
        
        PageResponse<ContatoDTO> contatos = contatoService.listarPaginado(
            pagina, tamanho, ordem, direcao);
        return ResponseEntity.ok(contatos);
    }
    
    @GetMapping("/buscar")
    @Operation(
        summary = "Buscar contatos por termo",
        description = "Busca contatos que contenham o termo no nome, telefone ou email"
    )
    public ResponseEntity<PageResponse<ContatoDTO>> buscarContatos(
            @Parameter(description = "Termo para busca", required = true, example = "joao")
            @RequestParam String termo,
            
            @Parameter(description = "Número da página", example = "0")
            @RequestParam(defaultValue = "0") int pagina,
            
            @Parameter(description = "Quantidade de itens por página", example = "10")
            @RequestParam(defaultValue = "10") int tamanho) {
        
        PageResponse<ContatoDTO> contatos = contatoService.buscarPorTermo(
            termo, pagina, tamanho);
        return ResponseEntity.ok(contatos);
    }
    
    @GetMapping("/{id}")
    @Operation(
        summary = "Buscar contato por ID",
        description = "Retorna um contato específico baseado no ID"
    )
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Contato encontrado"),
        @ApiResponse(responseCode = "404", description = "Contato não encontrado")
    })
    public ResponseEntity<ContatoDTO> buscarContatoPorId(
            @Parameter(description = "ID do contato", required = true, example = "1")
            @PathVariable Long id) {
        ContatoDTO contato = contatoService.buscarPorId(id);
        return ResponseEntity.ok(contato);
    }
    
    @PutMapping("/{id}")
    @Operation(
        summary = "Atualizar contato",
        description = "Atualiza os dados de um contato existente"
    )
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Contato atualizado com sucesso"),
        @ApiResponse(responseCode = "404", description = "Contato não encontrado"),
        @ApiResponse(responseCode = "400", description = "Dados inválidos"),
        @ApiResponse(responseCode = "409", description = "Email já cadastrado para outro contato")
    })
    public ResponseEntity<ContatoDTO> atualizarContato(
            @Parameter(description = "ID do contato a ser atualizado", required = true, example = "1")
            @PathVariable Long id,
            
            @Parameter(description = "Novos dados do contato", required = true)
            @Valid @RequestBody ContatoRequest request) {
        
        ContatoDTO contatoAtualizado = contatoService.atualizar(id, request);
        return ResponseEntity.ok(contatoAtualizado);
    }
    
    @DeleteMapping("/{id}")
    @Operation(
        summary = "Excluir contato",
        description = "Remove um contato da agenda"
    )
    @ApiResponses(value = {
        @ApiResponse(responseCode = "204", description = "Contato excluído com sucesso"),
        @ApiResponse(responseCode = "404", description = "Contato não encontrado")
    })
    public ResponseEntity<Void> excluirContato(
            @Parameter(description = "ID do contato a ser excluído", required = true, example = "1")
            @PathVariable Long id) {
        contatoService.excluir(id);
        return ResponseEntity.noContent().build();
    }
}