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
        public static final String TITLE = "title";
        public static final String REVIEWS = "reviews";
        public static final String REVIEW = "review";
        public static final String TYPE = "type";
        public static final String DEADLINE = "deadline";
        public static final String AUTHORS = "authors";
        public static final String CONTACT = "contact";

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
        public static final String PAPER_IDS = "paper_ids";
        public static final String SUBMITTER_ID = "submitter_id";
        public static final String FILE_NAME = "file_name";
        public static final String FILE_EXTENSION = "file_extension";
        public static final String REVISION_NO = "revision_no";

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
