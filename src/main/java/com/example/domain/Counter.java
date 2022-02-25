package com.example.domain;

import com.akkaserverless.javasdk.valueentity.ValueEntityContext;
import com.example.CounterApi;
import com.google.protobuf.Empty;

// This class was initially generated based on the .proto definition by Akka Serverless tooling.
// This is the implementation for the Value Entity Service described in your com/example/api.proto file.
//
// As long as this file exists it will not be overwritten: you can maintain it yourself,
// or delete it so it is regenerated as needed.

public class Counter extends AbstractCounter {
  @SuppressWarnings("unused")
  private final String entityId;

  public Counter(ValueEntityContext context) {
    this.entityId = context.entityId();
  }

  @Override
  public com.example.domain.CounterDomain.CounterState emptyState() {
    return CounterDomain.CounterState.getDefaultInstance();
  }

  @Override
  public Effect<Empty> increase(CounterDomain.CounterState currentState, CounterApi.IncreaseValue increaseValue) {
    if (increaseValue.getValue() < 0) {
      return effects().error("Increase requires a positive value. It was [" +increaseValue.getValue() + "].");
    }
    CounterDomain.CounterState newState = currentState.toBuilder().setValue(currentState.getValue() +
            increaseValue.getValue()).build();
    return effects()
            .updateState(newState)
            .thenReply(Empty.getDefaultInstance());
  }

  @Override
  public Effect<Empty> decrease(CounterDomain.CounterState currentState, CounterApi.DecreaseValue decreaseValue) {
    if (decreaseValue.getValue() < 0) {
      return effects().error("Decrease requires a positive value. It was [" + decreaseValue.getValue() + "].");
    }
    CounterDomain.CounterState newState = currentState.toBuilder().setValue(currentState.getValue() -
            decreaseValue.getValue()).build();
    return effects()
            .updateState(newState)
            .thenReply(Empty.getDefaultInstance());
  }

  @Override
  public Effect<Empty> reset(CounterDomain.CounterState currentState, CounterApi.ResetValue resetValue) {
    CounterDomain.CounterState newState = currentState.toBuilder().clearValue().build();
    return effects()
            .updateState(newState)
            .thenReply(Empty.getDefaultInstance());
  }

  @Override
  public Effect<CounterApi.CurrentCounter> getCurrentCounter(CounterDomain.CounterState currentState, CounterApi.GetCounter getCounter) {
    CounterApi.CurrentCounter current = CounterApi.CurrentCounter.newBuilder()
            .setValue(currentState.getValue()).build();
    return effects().reply(current);

  }
}
