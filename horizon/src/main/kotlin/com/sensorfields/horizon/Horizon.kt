package com.sensorfields.horizon

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class Horizon<State, Event, Effect, Action>(
    private val combine: Combine<State, Event, Effect, Action>,
    private val transform: Transform<Action, Event>
) {
    fun dispatch(event: Event) {}

    fun states(): Flow<State> = flow {}

    fun effects(): Flow<Effect> = flow { }
}

data class Result<State, Effect, Action>(
    val state: State? = null,
    val effects: List<Effect> = listOf(),
    val actions: List<Action> = listOf()
)

typealias Combine<State, Event, Effect, Action> = (State, Event) -> Result<State, Effect, Action>

typealias Transform<Action, Event> = (Action) -> Flow<Event>
