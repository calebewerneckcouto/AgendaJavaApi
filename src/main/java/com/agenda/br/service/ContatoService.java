package com.agenda.br.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.agenda.br.model.Contato;
import com.agenda.br.model.dto.ContatoDTO;
import com.agenda.br.model.dto.ContatoRequest;
import com.agenda.br.model.dto.PageResponse;
import com.agenda.br.repository.ContatoRepository;

@Service
@Transactional
public class ContatoService {
    
    private final ContatoRepository contatoRepository;  
    
    // Adicione este construtor
    public ContatoService(ContatoRepository contatoRepository) {
        this.contatoRepository = contatoRepository;
    }
    
    private ContatoDTO toDTO(Contato contato) {
        return new ContatoDTO(
            contato.getId(),
            contato.getNome(),
            contato.getTelefone(),
            contato.getEmail(),
            contato.getEndereco(),
            contato.getObservacoes(),
            contato.getDataCadastro(),
            contato.getDataAtualizacao()
        );
    }
    
    private Contato toEntity(ContatoRequest request) {
        Contato contato = new Contato();
        contato.setNome(request.getNome());
        contato.setTelefone(request.getTelefone());
        contato.setEmail(request.getEmail());
        contato.setEndereco(request.getEndereco());
        contato.setObservacoes(request.getObservacoes());
        return contato;
    }
    
    @Transactional
    public ContatoDTO salvar(ContatoRequest request) {
        if (request.getEmail() != null && !request.getEmail().isEmpty()) {
            if (contatoRepository.existsByEmail(request.getEmail())) {
                throw new RuntimeException("Email já cadastrado");
            }
        }
        
        Contato contato = toEntity(request);
        contato = contatoRepository.save(contato);
        return toDTO(contato);
    }
    
    @Transactional(readOnly = true)
    public PageResponse<ContatoDTO> listarPaginado(int pagina, int tamanho, String ordem, String direcao) {
        Sort sort = direcao.equalsIgnoreCase("desc") 
            ? Sort.by(ordem).descending() 
            : Sort.by(ordem).ascending();
        
        Pageable pageable = PageRequest.of(pagina, tamanho, sort);
        Page<Contato> contatosPage = contatoRepository.findAll(pageable);
        
        List<ContatoDTO> contatosDTO = contatosPage.getContent()
            .stream()
            .map(this::toDTO)
            .collect(Collectors.toList());
        
        return new PageResponse<>(
            contatosDTO,
            contatosPage.getNumber(),
            contatosPage.getSize(),
            contatosPage.getTotalElements(),
            contatosPage.getTotalPages(),
            contatosPage.isLast()
        );
    }
    
    @Transactional(readOnly = true)
    public PageResponse<ContatoDTO> buscarPorTermo(String termo, int pagina, int tamanho) {
        Pageable pageable = PageRequest.of(pagina, tamanho, Sort.by("nome").ascending());
        Page<Contato> contatosPage = contatoRepository.buscarPorTermo(termo, pageable);
        
        List<ContatoDTO> contatosDTO = contatosPage.getContent()
            .stream()
            .map(this::toDTO)
            .collect(Collectors.toList());
        
        return new PageResponse<>(
            contatosDTO,
            contatosPage.getNumber(),
            contatosPage.getSize(),
            contatosPage.getTotalElements(),
            contatosPage.getTotalPages(),
            contatosPage.isLast()
        );
    }
    
    @Transactional(readOnly = true)
    public ContatoDTO buscarPorId(Long id) {
        Contato contato = contatoRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("Contato não encontrado com ID: " + id));
        return toDTO(contato);
    }
    
    @Transactional
    public ContatoDTO atualizar(Long id, ContatoRequest request) {
        Contato contato = contatoRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("Contato não encontrado com ID: " + id));
        
        if (request.getEmail() != null && !request.getEmail().isEmpty()) {
            if (contatoRepository.existsByEmailAndIdNot(request.getEmail(), id)) {
                throw new RuntimeException("Email já cadastrado para outro contato");
            }
        }
        
        contato.setNome(request.getNome());
        contato.setTelefone(request.getTelefone());
        contato.setEmail(request.getEmail());
        contato.setEndereco(request.getEndereco());
        contato.setObservacoes(request.getObservacoes());
        
        contato = contatoRepository.save(contato);
        return toDTO(contato);
    }
    
    @Transactional
    public void excluir(Long id) {
        if (!contatoRepository.existsById(id)) {
            throw new RuntimeException("Contato não encontrado com ID: " + id);
        }
        contatoRepository.deleteById(id);
    }
}