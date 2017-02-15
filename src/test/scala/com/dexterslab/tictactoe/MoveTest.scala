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

  it should "return an invalid move if someone tries to move to an already occupied cell" in {
    val occupiedPosition = Position(0, 1)
    val newPosition = Position(0, 1)
    val playerO = PlayerO
    val playerX = PlayerX
    val occupiedCell = OccupiedCell(occupiedPosition, playerO)
    val currentBoard = InPlayBoard(List(occupiedCell))
    val moveResult = move(currentBoard, newPosition, playerX)

    moveResult should be(InvalidMove)
  }

  it should "return a successful move with a finished board if nine moves have been played" in {
    val positions = (for (i <- 0 to 2;
                          j <- 0 to 2) yield Position(i, j)).toList.take(8)
    val playerO = PlayerO
    val playerX = PlayerX
    val cells = positions.map(OccupiedCell(_, playerO))
    val newPosition = Position(2, 2)
    val currentBoard = InPlayBoard(cells)
    val successfulMove = SuccessfulMove(FinishedBoard(OccupiedCell(newPosition, playerX) :: cells))
    val moveResult = move(currentBoard, newPosition, playerX)

    moveResult should be(successfulMove)
  }

  it should "return a successful move with a winning board if a player wins" in {
    val playerO = PlayerO
    val playerX = PlayerX
    val newPosition = Position(0, 2)
    val cells = List(OccupiedCell(Position(0,0), playerX), OccupiedCell(Position(1,0), playerO),
                     OccupiedCell(Position(0,1), playerX), OccupiedCell(Position(2,0), playerO))
    val currentBoard = InPlayBoard(cells)
    val successfulMove = SuccessfulMove(WinningBoard(OccupiedCell(newPosition, playerX) :: cells))
    val moveResult = move(currentBoard, newPosition, playerX)

    moveResult should be(successfulMove)
  }

}
