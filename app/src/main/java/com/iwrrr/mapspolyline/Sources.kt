package com.iwrrr.mapspolyline

import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

object Sources {

    val json = """
        {
            "status": true,
            "message": "Success",
            "data": {
                "route": [
                    {
                        "latitude": -6.247968,
                        "longitude": 107.095777
                    },
                    {
                        "latitude": -6.24832,
                        "longitude": 107.09625
                    },
                    {
                        "latitude": -6.24848,
                        "longitude": 107.09624
                    },
                    {
                        "latitude": -6.24863,
                        "longitude": 107.09622
                    },
                    {
                        "latitude": -6.24878,
                        "longitude": 107.0962
                    },
                    {
                        "latitude": -6.24921,
                        "longitude": 107.09616
                    },
                    {
                        "latitude": -6.24949,
                        "longitude": 107.09613
                    },
                    {
                        "latitude": -6.24967,
                        "longitude": 107.09611
                    },
                    {
                        "latitude": -6.24988,
                        "longitude": 107.09607
                    },
                    {
                        "latitude": -6.25013,
                        "longitude": 107.09599
                    },
                    {
                        "latitude": -6.25027,
                        "longitude": 107.09596
                    },
                    {
                        "latitude": -6.2504,
                        "longitude": 107.09698
                    },
                    {
                        "latitude": -6.25042,
                        "longitude": 107.09707
                    },
                    {
                        "latitude": -6.25045,
                        "longitude": 107.09713
                    },
                    {
                        "latitude": -6.25063,
                        "longitude": 107.0974
                    },
                    {
                        "latitude": -6.25069,
                        "longitude": 107.09751
                    },
                    {
                        "latitude": -6.25073,
                        "longitude": 107.09762
                    },
                    {
                        "latitude": -6.25134,
                        "longitude": 107.09751
                    },
                    {
                        "latitude": -6.25161,
                        "longitude": 107.09748
                    },
                    {
                        "latitude": -6.25191,
                        "longitude": 107.09743
                    },
                    {
                        "latitude": -6.25204,
                        "longitude": 107.0974
                    },
                    {
                        "latitude": -6.25219,
                        "longitude": 107.09738
                    },
                    {
                        "latitude": -6.25246,
                        "longitude": 107.09733
                    },
                    {
                        "latitude": -6.25269,
                        "longitude": 107.09728
                    },
                    {
                        "latitude": -6.25295,
                        "longitude": 107.09722
                    },
                    {
                        "latitude": -6.25392,
                        "longitude": 107.09703
                    },
                    {
                        "latitude": -6.254,
                        "longitude": 107.09701
                    },
                    {
                        "latitude": -6.25409,
                        "longitude": 107.09697
                    },
                    {
                        "latitude": -6.25415,
                        "longitude": 107.09693
                    },
                    {
                        "latitude": -6.25421,
                        "longitude": 107.0969
                    },
                    {
                        "latitude": -6.25435,
                        "longitude": 107.09686
                    },
                    {
                        "latitude": -6.25451,
                        "longitude": 107.09767
                    },
                    {
                        "latitude": -6.25461,
                        "longitude": 107.09782
                    },
                    {
                        "latitude": -6.25475,
                        "longitude": 107.098
                    },
                    {
                        "latitude": -6.25488,
                        "longitude": 107.09824
                    },
                    {
                        "latitude": -6.2549,
                        "longitude": 107.09839
                    },
                    {
                        "latitude": -6.25491,
                        "longitude": 107.09853
                    },
                    {
                        "latitude": -6.25492,
                        "longitude": 107.09866
                    },
                    {
                        "latitude": -6.25478,
                        "longitude": 107.09893
                    },
                    {
                        "latitude": -6.25476,
                        "longitude": 107.099
                    },
                    {
                        "latitude": -6.25487,
                        "longitude": 107.09978
                    },
                    {
                        "latitude": -6.2549,
                        "longitude": 107.09989
                    },
                    {
                        "latitude": -6.25518,
                        "longitude": 107.10052
                    },
                    {
                        "latitude": -6.25546,
                        "longitude": 107.10137
                    },
                    {
                        "latitude": -6.25553,
                        "longitude": 107.10164
                    },
                    {
                        "latitude": -6.25588,
                        "longitude": 107.10151
                    },
                    {
                        "latitude": -6.25595,
                        "longitude": 107.10151
                    },
                    {
                        "latitude": -6.256,
                        "longitude": 107.10155
                    },
                    {
                        "latitude": -6.25628,
                        "longitude": 107.10235
                    }
                ]
            }
        }
    """.trimIndent()

    fun getResultRoutes(): ResultRoutes {
        val gson = Gson()
        return gson.fromJson(json, object : TypeToken<ResultRoutes>() {}.type)
    }
}