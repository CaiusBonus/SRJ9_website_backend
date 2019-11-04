package com.srj9.service

import org.springframework.mail.SimpleMailMessage
import org.springframework.mail.javamail.JavaMailSender
import org.springframework.stereotype.Component
import javax.inject.Inject
import org.apache.tomcat.jni.SSL.setPassword
import org.springframework.context.annotation.Bean
import org.springframework.mail.javamail.JavaMailSenderImpl
import org.springframework.beans.factory.annotation.Autowired






@Component
class EmailServiceImpl: EmailService {

    @Autowired
    var emailSender: JavaMailSender? = null

    override fun sendSimpleMessage(to: String, subject: String, text: String) {
        var message = SimpleMailMessage()
        message.setTo(to);
        message.setSubject(subject);
        message.setText(text);
        emailSender!!.send(message);
    }

    override fun sendSimpleMessageUsingTemplate(to: String, subject: String, template: SimpleMailMessage, vararg templateArgs: String) {
        val text = String.format(template.text!!, templateArgs)
        sendSimpleMessage(to,subject,text)
    }

    override fun sendMessageWithAttachment(to: String, subject: String, text: String, pathToAttachment: String) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

//    @Bean
//    fun getJavaMailSender(): JavaMailSender {
//        val mailSender = JavaMailSenderImpl()
//        mailSender.host = "smtp.gmail.com"
//        mailSender.port = 587
//
//        mailSender.username = "my.gmail@gmail.com"
//        mailSender.password = "password"
//
//        val props = mailSender.javaMailProperties
//        props["mail.transport.protocol"] = "smtp"
//        props["mail.smtp.auth"] = "true"
//        props["mail.smtp.starttls.enable"] = "true"
//        props["mail.debug"] = "true"
//
//        return mailSender
//    }

}