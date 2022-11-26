package com.sams.samsapi.util;

public class CodeSmellFixer {
    public class LoggerCase{
        public static final String USER_UN_AUTHORIZED = "User Unauthorized : {0}";
        public static final String METHODNAME = "methodName ::: {0}";
        public static final String EXCEPTION = "Exception ::: {0}";

        private LoggerCase(){
        
        }
    }
    public class UpperCase{
     
        private UpperCase(){
        
        }
    }

    public class LowerCase{
        public static final String RATING = "rating";

        private LowerCase(){
        
        }
    }

    public class CamelCase{

        private CamelCase(){
        
        }
    }

    public class SnakeCase{
        public static final String PCM_ID = "pcm_id";
        public static final String PAPER_ID = "paper_id";

        private SnakeCase(){
        
        }
    }

    public class CapitalizationCase{

        private CapitalizationCase(){
        
        }
    }

    public class SpecialCharacter{

        private SpecialCharacter(){
        
        }
    }
    
    private CodeSmellFixer(){
        
    }

    public static class ExceptionThrower{
        public static void throwInvalidBody() throws IllegalArgumentException{
            throw new IllegalArgumentException();
        }

        private ExceptionThrower(){
        
        }
    }


}
