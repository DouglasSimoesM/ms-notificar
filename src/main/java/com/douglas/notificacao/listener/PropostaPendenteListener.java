package com.douglas.notificacao.listener;

import com.douglas.notificacao.constante.MensagemConstante;
import com.douglas.notificacao.domain.Proposta;
import com.douglas.notificacao.service.NotificacaoSnsService;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class PropostaPendenteListener {

    @Autowired
    private NotificacaoSnsService notificacaoSnsService;
    //RabbitListener é um "ouvinte"
    @RabbitListener(queues = "${rabbitmq.queue.proposta.pendente}")
    public void PropostaPendente(Proposta proposta){
        String mensage = String.format(MensagemConstante.PROPOSTA_EM_ANALISE, proposta.getUsuario().getNome());
        notificacaoSnsService.notificar(proposta.getUsuario().getTelefone(), mensage);

    }
}
