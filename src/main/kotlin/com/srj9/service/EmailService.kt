package com.srj9.service

import org.springframework.mail.SimpleMailMessage
import org.springframework.mail.javamail.JavaMailSender
import org.springframework.stereotype.Component
import javax.inject.Inject


@Component
class EmailService {

//    @Inject
//    lateinit var emailSender: JavaMailSender

    fun sendConfirmationMessageToReceiver(receiver: String, subject: String, text: String) {
//        var message = SimpleMailMessage()
//        message.setTo(receiver)
//        message.setSubject(subject)
//        message.setText(text)
//        emailSender.send(message)
    }

    fun sendMessageToReceivers(receivers: List<String>, subject: String, text: String) {
        receivers.forEach { to ->
//            var message = SimpleMailMessage()
//            message.setTo(to)
//            message.setSubject(subject)
//            message.setText(text)
//            emailSender.send(message)
        }
    }

    fun createMessage(title: String, receiver: String, date: String, link: String): String {
        var template: String = "Dobry den, \n" + "bola vytvorena nova rezervacia"
        return String.format(template,title,receiver,date,link)
    }
}