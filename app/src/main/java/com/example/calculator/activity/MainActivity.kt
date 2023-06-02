package com.example.calculator.activity

import android.os.Bundle
import android.view.View
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import com.example.calculator.databinding.ActivityMainBinding


class MainActivity : AppCompatActivity(), View.OnClickListener {
    lateinit var binding: ActivityMainBinding

    companion object {
        private const val TAG = "MainActivity"
    }

    private var process = StringBuilder()

    private var isEqual = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        initClick()
    }

    private fun initClick() {
        binding.btnClear.setOnClickListener(this)
        binding.btnBack.setOnClickListener(this)
        binding.btnEqual.setOnClickListener(this)

        binding.btnPoint.setOnClickListener(this)
        binding.btn0.setOnClickListener(this)
        binding.btn00.setOnClickListener(this)
        binding.btn1.setOnClickListener(this)
        binding.btn2.setOnClickListener(this)
        binding.btn3.setOnClickListener(this)
        binding.btn4.setOnClickListener(this)
        binding.btn5.setOnClickListener(this)
        binding.btn6.setOnClickListener(this)
        binding.btn7.setOnClickListener(this)
        binding.btn8.setOnClickListener(this)
        binding.btn9.setOnClickListener(this)

        binding.btnAdd.setOnClickListener(this)
        binding.btnMinus.setOnClickListener(this)
        binding.btnMultiply.setOnClickListener(this)
        binding.btnDiv.setOnClickListener(this)

        binding.btnPercentage.setOnClickListener(this)
    }

    override fun onClick(v: View) {
        when (v.id) {
            binding.btnClear.id -> {
                //清空表达式
                process.clear()
                binding.tvProcess.text = ""
                binding.tvResult.text = ""
            }

            binding.btnBack.id -> {
                //非空，减去字符
                if (process.isNotBlank()) {
                    process.deleteAt(process.lastIndex)
                    binding.tvProcess.text = process.toString()
                }
            }

            binding.btnEqual.id -> {
                isEqual = true
                binding.tvProcess.textSize = 30f
                binding.tvResult.textSize = 50f
            }

            binding.btnPoint.id -> {
                //数字加上小数点
                if (process.isEmpty()) {
                    process.append("0.")
                }
                if (process.last().isDigit()) {
                    process.append(".")
                }
                binding.tvProcess.text = process.toString()
            }

            binding.btnPercentage.id -> {
                if (process.isBlank()) {
                    return
                }
                if (process.last().isDigit()) {
                    process.append("%")
                    binding.tvProcess.text = process.toString()
                    getAddResult(process.toString())
                }
            }

            binding.btn0.id,
            binding.btn1.id,
            binding.btn2.id,
            binding.btn3.id,
            binding.btn4.id,
            binding.btn5.id,
            binding.btn6.id,
            binding.btn7.id,
            binding.btn8.id,
            binding.btn9.id,
            binding.btn00.id -> {
                if (isEqual) {
                    isEqual = false
                    process.clear()
                    binding.tvProcess.text = ""
                    binding.tvProcess.textSize = 50f
                    binding.tvResult.text = ""
                    binding.tvResult.textSize = 30f
                }
                //数字拼接
                val content = (v as Button).text.toString()
                process.append(content)
                binding.tvProcess.text = process.toString()
                getAddResult(process.toString())
            }

            binding.btnAdd.id -> {
                //加法
                if (process.last().isDigit() || (process.last().toString() == "%")) {
                    process.append("+")
                    binding.tvProcess.text = process.toString()
                }
                getAddResult(process.toString())
            }

            binding.btnMinus.id -> {
                //减法
                if (process.last().isDigit() || (process.last().toString() == "%")) {
                    process.append("-")
                    binding.tvProcess.text = process.toString()
                }
            }

            binding.btnMultiply.id -> {
                //乘法
                if (process.last().isDigit() ||(process.last().toString() == "%")) {
                    process.append("*")
                    binding.tvProcess.text = process.toString()
                }
            }

            binding.btnDiv.id -> {
                //除法
                if (process.last().isDigit() || (process.last().toString() == "%")) {
                    process.append("÷")
                    binding.tvProcess.text = process.toString()
                }
            }
        }
    }

    //整体思路，先通过加法进行分割，再对分割的每个部分，进行减法运算 ，减法则进行除法运算，除法再进行乘法运算

    /**
     * 加法
     */
    private fun getAddResult(formula: String) {
        val process = formula.replace("%", "÷100")
        var numResult = 0.0
        val addList = process.split("+")
        addList.forEach {
            if (it.isNotBlank()) {
                numResult += getMinusResult(it)
            }
        }
        if (numResult % 1.0 == 0.0) {
            binding.tvResult.text = numResult.toLong().toString()
        } else {
            binding.tvResult.text = numResult.toString()
        }

    }

    private fun getMinusResult(formula: String): Double {
        val minusList = formula.split("-")
        if (minusList.isNotEmpty()) {
            var minusResult = getMultiplyResult(minusList[0]) * 2
            minusList.forEach {
                if (it.isNotBlank()) {
                    minusResult -= getMultiplyResult(it)
                }
            }
            return minusResult
        }
        return 0.0
    }

    private fun getMultiplyResult(formula: String): Double {
        val multiplierList = formula.split("*")
        if (multiplierList.isNotEmpty()) {
            var multiplyResult = 1.0
            multiplierList.forEach {
                if (it.isNotBlank()) {
                    multiplyResult *= getDivResult(it)
                }
            }
            return multiplyResult
        }
        return 0.0
    }

    private fun getDivResult(formula: String): Double {
        val divList = formula.split("÷")
        if (divList.isNotEmpty()) {
            var divResult = divList[0].toDouble() * divList[0].toDouble()
            divList.forEach {
                if (it.isNotBlank()) {
                    divResult /= it.toDouble()
                }
            }
            return divResult
        }
        return 1.0
    }
}

