package com.dexterslab.tictactoe

import com.dexterslab.tictactoe.Api.move
import com.dexterslab.tictactoe.DataTypes._
import org.scalatest.{FlatSpec, Matchers}

class MoveTest extends FlatSpec with Matchers {

  behavior of "An Empty Board"

  it should "be successful and return an in-play board with the position occupied by player" in {
    val position = Position(0, 1)
    val player = PlayerO
    val successfulMove = SuccessfulMove(InPlayBoard(List(OccupiedCell(position, player))))
    val moveResult = move(EmptyBoard, position, player)
    moveResult should be(successfulMove)
  }

  behavior of "An In-Play Board"

  it should "be successful and return an in-play board with the position occupied by player when game has not finished" in {
    val occupiedPosition = Position(0, 1)
    val newPosition = Position(0, 0)
    val playerO = PlayerO
    val playerX = PlayerX
    val occupiedCell = OccupiedCell(occupiedPosition, playerO)
    val currentBoard = InPlayBoard(List(occupiedCell))
    val successfulMove = SuccessfulMove(InPlayBoard(List(OccupiedCell(newPosition, playerX), occupiedCell)))
    val moveResult = move(currentBoard, newPosition, playerX)

    moveResult should be(successfulMove)
  }

}
