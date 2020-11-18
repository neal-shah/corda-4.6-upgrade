package com.template.schemas

import net.corda.core.schemas.MappedSchema
import net.corda.core.schemas.PersistentState
import org.hibernate.annotations.Type
import java.util.*
import javax.persistence.Column
import javax.persistence.Entity
import javax.persistence.Table


object IOUSchema

object IOUSchemaV1 : MappedSchema(
        schemaFamily = IOUSchema.javaClass,
        version = 1,
        mappedTypes = listOf(PersistentIOU::class.java)) {

    override val migrationResource: String?
        get() = "iou.changelog-master";

    @Entity
    @Table(name = "iou_states")
    class PersistentIOU(

            @Column(name = "lender")
            var lenderName: String,

            @Column(name = "borrower")
            var borrowerName: String,

            @Column(name = "value")
            var value: Int,

            @Column(name = "linear_id")
            @Type(type = "uuid-char")
            var linearId: UUID

    ) : PersistentState() {
        constructor(): this("", "", 0, UUID.randomUUID())
    }
}