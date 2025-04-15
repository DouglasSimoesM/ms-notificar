package com.douglas.notificacao.listener;

import com.douglas.notificacao.constante.MensagemConstante;
import com.douglas.notificacao.domain.Proposta;
import com.douglas.notificacao.service.NotificacaoSnsService;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class PropostaConcluidaListener {

    @Autowired
    private NotificacaoSnsService notificacaoSnsService;

    //RabbitListener é um "ouvinte"
    @RabbitListener(queues = "${rabbitmq.queue.proposta.concluida}")
    public void PropostaPendente(Proposta proposta){
        if (!proposta.getAprovada()){
            String mensage = String.format(MensagemConstante.PROPOSTA_CONCLUIDA_REPROVADA, proposta.getUsuario().getNome(), proposta.getObservacao());
            notificacaoSnsService.notificar(proposta.getUsuario().getTelefone(), mensage);
        } else {
        String mensage = String.format(MensagemConstante.PROPOSTA_CONCLUIDA_APROVADA, proposta.getUsuario().getNome());
        notificacaoSnsService.notificar(proposta.getUsuario().getTelefone(), mensage);

        }

    }
}
