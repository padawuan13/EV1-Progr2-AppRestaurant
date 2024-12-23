package cl.fhernandez.android.ev1progr2apprestaurant

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import android.widget.EditText
import android.widget.TextView
import android.widget.Switch
import android.text.Editable
import android.text.TextWatcher

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val pastel = ItemMenu("Pastel de Choclo", 12000)
        val cazuela = ItemMenu("Cazuela", 10000)

        val cuentaMesa = CuentaMesa()

        val pastelQuantity = findViewById<EditText>(R.id.pastelQuantity)
        val pastelSubtotal = findViewById<TextView>(R.id.pastelSubtotal)

        val cazuelaQuantity = findViewById<EditText>(R.id.cazuelaQuantity)
        val cazuelaSubtotal = findViewById<TextView>(R.id.cazuelaSubtotal)

        val subtotalComida = findViewById<TextView>(R.id.subtotalComida)
        val propinaSwitch = findViewById<Switch>(R.id.propinaSwitch)
        val montoPropina = findViewById<TextView>(R.id.montoPropina)
        val totalGeneralText = findViewById<TextView>(R.id.totalGeneralText)

        fun actualizarTotales() {
            val totalSinPropina = cuentaMesa.calcularTotalSinPropina()
            val propina = if (cuentaMesa.aceptaPropina) (totalSinPropina * 0.1).toInt() else 0
            val totalConPropina = totalSinPropina + propina

            subtotalComida.text = "Comida: $${totalSinPropina}"
            montoPropina.text = "$${propina}"
            totalGeneralText.text = "TOTAL: $${totalConPropina}"
        }

        pastelQuantity.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {
                val cantidad = pastelQuantity.text.toString().toIntOrNull() ?: 0
                cuentaMesa.agregarItem(pastel, cantidad)
                val subtotalPastel = cantidad * pastel.precio
                pastelSubtotal.text = "$${subtotalPastel}"
                actualizarTotales()
            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}
        })

        cazuelaQuantity.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {
                val cantidad = cazuelaQuantity.text.toString().toIntOrNull() ?: 0
                cuentaMesa.agregarItem(cazuela, cantidad)
                val subtotalCazuela = cantidad * cazuela.precio
                cazuelaSubtotal.text = "$${subtotalCazuela}"
                actualizarTotales()
            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}
        })

        propinaSwitch.setOnCheckedChangeListener { _, isChecked ->
            cuentaMesa.aceptaPropina = isChecked
            actualizarTotales()
        }
    }
}



