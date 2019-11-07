package com.srj9.service

import com.srj9.model.GymReservation
import org.springframework.mail.SimpleMailMessage
import org.springframework.mail.javamail.JavaMailSender
import org.springframework.stereotype.Component
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.scheduling.annotation.Async
import java.text.SimpleDateFormat
import java.util.*

@Component
class EmailServiceImpl: EmailService {

    @Autowired
    var emailSender: JavaMailSender? = null

    override fun sendSimpleMessage(to: String, subject: String, text: String) {
        val message = SimpleMailMessage()
        message.setTo(to);
        message.setSubject(subject);
        message.setText(text);
        emailSender!!.send(message);
    }

    @Async
    fun sendEmail(email: SimpleMailMessage) {
        emailSender!!.send(email)
    }

    override fun sendSimpleMessageUsingTemplate(to: String, subject: String, template: SimpleMailMessage, vararg templateArgs: String) {
        val text = String.format(template.text!!, templateArgs)
        sendSimpleMessage(to,subject,text)
    }

    override fun sendMessageWithAttachment(to: String, subject: String, text: String, pathToAttachment: String) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    fun sendMessageToUser(to: String, gymReservation: GymReservation) {
        val message = SimpleMailMessage()
        message.setTo(to)
        message.setSubject("Rezervácia bola vytvorená")
        message.setText("Dobrý deň, Študentská rada Jedlíkova 9 Vám ďakuje, " +
                "že využívate naše služby. Vaša rezervácia na telocvičňu T" + gymReservation.gym_number + " dňa " + SimpleDateFormat("dd MMMM yyyy").format(gymReservation.date) +
                " od " + gymReservation.time_from + " do " + gymReservation.time_until + " je vytvorená.")
        emailSender!!.send(message)
    }

    fun sendMessageToSportOfficer(to: String, gymReservation: GymReservation) {
        val message = SimpleMailMessage()
        message.setTo(to)
        message.setSubject("Nová rezervácia bola vytvorená")
        message.setText("Rezervácia na telocvičňu T" + gymReservation.gym_number + " dňa " + SimpleDateFormat("dd MMMM yyyy").format(gymReservation.date) +
                " od " + gymReservation.time_from + " do " + gymReservation.time_until + " je vytvorená.")
        emailSender!!.send(message)

    }
}