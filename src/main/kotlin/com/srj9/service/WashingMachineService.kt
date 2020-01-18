package com.srj9.service

import com.srj9.model.WashingMachine
import com.srj9.repository.WashingMachineRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service

@Service
class WashingMachineService {

    @Autowired
    lateinit var washingMachineRepository: WashingMachineRepository

    fun getAllWashingMachines(): List<WashingMachine> {
        return washingMachineRepository.findAll()
    }

    fun getSingleWashingMachine(id: Long): WashingMachine {
        return washingMachineRepository.getOne(id)
    }
}