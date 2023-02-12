package uy.amn.dummygen.domain.models

/**
 * "columns": [
 * {
 *      "name": "id",
 *      "dataType": "int",
 *      "generator": "increment",
 *      "params": {
 *          "start": 1,
 *          "step": 1
 *      }
 * },
 *
 */

data class ColumnSettings(val name: String, val dataType: String, val generator: String, val params: Map<String, Any>)
