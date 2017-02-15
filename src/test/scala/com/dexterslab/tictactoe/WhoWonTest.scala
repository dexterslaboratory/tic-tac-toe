package com.dexterslab.tictactoe

import com.dexterslab.tictactoe.Api.whoWon
import com.dexterslab.tictactoe.DataTypes._
import org.scalatest.{FlatSpec, Matchers}

class WhoWonTest extends FlatSpec with Matchers {
  behavior of "a winning board"

  it should "return the winner when the same player has occupied a row" in {
    val winningBoard = WinningBoard(List(
      OccupiedCell(Position(0, 1), PlayerX),
      OccupiedCell(Position(0, 2), PlayerX),
      OccupiedCell(Position(0, 3), PlayerX)
    ))
    val expectedWinner = Winner(PlayerX)
    val returnedWinner = whoWon(winningBoard)

    returnedWinner should be (expectedWinner)
  }

  it should "return the winner when the same player has occupied a column" in {
    val winningBoard = WinningBoard(List(
      OccupiedCell(Position(0, 0), PlayerX),
      OccupiedCell(Position(1, 0), PlayerX),
      OccupiedCell(Position(2, 0), PlayerX)
    ))
    val expectedWinner = Winner(PlayerX)
    val returnedWinner = whoWon(winningBoard)

    returnedWinner should be (expectedWinner)
  }

  it should "return the winner when the same player has occupied the main diagonal" in {
    val winningBoard = WinningBoard(List(
      OccupiedCell(Position(0, 0), PlayerX),
      OccupiedCell(Position(1, 1), PlayerX),
      OccupiedCell(Position(2, 2), PlayerX)
    ))
    val expectedWinner = Winner(PlayerX)
    val returnedWinner = whoWon(winningBoard)

    returnedWinner should be (expectedWinner)
  }

  it should "return the winner when the same player has occupied the cross diagonal" in {
    val winningBoard = WinningBoard(List(
      OccupiedCell(Position(0, 2), PlayerX),
      OccupiedCell(Position(1, 1), PlayerX),
      OccupiedCell(Position(2, 0), PlayerX)
    ))
    val expectedWinner = Winner(PlayerX)
    val returnedWinner = whoWon(winningBoard)

    returnedWinner should be (expectedWinner)
  }

}
