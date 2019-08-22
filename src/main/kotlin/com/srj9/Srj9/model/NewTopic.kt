package com.srj9.Srj9.model

import lombok.Data
import javax.persistence.*

@Data
@Entity
@Table(name = "NEW_TOPIC")
class NewTopic {

    companion object {
        const val SEQ = "NEW_TOPIC_ID_SEQ"
    }

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = SEQ)
    @SequenceGenerator(name = SEQ, sequenceName = SEQ, allocationSize = 1)
    @Column(name = "id")
    var id: Long? = null

    @Column(name="name")
    var name: String? = null

    @Column(name="description")
    var description: String? = null
}
