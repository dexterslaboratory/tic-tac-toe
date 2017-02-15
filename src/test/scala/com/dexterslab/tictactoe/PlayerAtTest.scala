package com.dexterslab.tictactoe

import com.dexterslab.tictactoe.Api.playerAt
import com.dexterslab.tictactoe.DataTypes._
import org.scalatest.{FlatSpec, Matchers}

class PlayerAtTest extends FlatSpec with Matchers {
  behavior of "playerAt"

  it should "return the player at a given position" in {
    val position = Position(1, 1)
    val board = InPlayBoard(List(OccupiedCell(Position(1, 1), PlayerX)))

    playerAt(board,position) should be (Some(PlayerX))
  }

  it should "return none if the given position is empty" in {
    val position = Position(1, 1)
    val board = InPlayBoard(List(EmptyCell(Position(1, 1))))

    playerAt(board,position) should be (None)
  }
}
