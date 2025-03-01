package org.example.model

import java.time.LocalDateTime
import java.util.UUID

class MedicalAppointment (val id: UUID?,
                          var patient: Patient,
                          var doctor: Doctor,
                          var dateTime: LocalDateTime){
}