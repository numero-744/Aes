package aes

// importcore
import spinal.core._

// component AddRoundKey <Tab>
// pin text AesState()
// pin key AesState()
// pout cipher AesState()
case class AddRoundKey() extends Component {
  val io = new Bundle {
    val text   = in  port AesState()
    val key    = in  port AesState()
    val cipher = out port AesState()
  }

  io.cipher.vals := io.text.vals ^ io.key.vals
}
