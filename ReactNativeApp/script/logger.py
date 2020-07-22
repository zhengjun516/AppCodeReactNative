import logging
import os
from datetime import datetime

class Logger:
    def __init__(self):
        self.logger = logging.getLogger("")
        self.logger.setLevel(logging.INFO)
        self.formatter = logging.Formatter('%(asctime)s [%(levelname)s] : %(message)s')
        self.streamhandler = logging.StreamHandler()
        self.streamhandler.setFormatter(self.formatter)
        self.logger.addHandler(self.streamhandler)
        self.logfile = None
        self.filehandler = None
        self.__perf_mode = False
    
    def is_perf_mode(self):
        return self.__perf_mode

    def enable_file_logger(self, log_name = None):
        postfix = "_" + log_name if log_name else ""
        self.logfile = '%s%s.log' % (str(datetime.timestamp(datetime.now())), postfix)
        self.filehandler = logging.FileHandler(self.logfile)
        self.filehandler.setFormatter(self.formatter)
        self.logger.addHandler(self.filehandler)
        self.__perf_mode = True
    
    def debug(self, *msg):
        self.__log(self.logger.debug, *msg)

    def info(self, *msg):
        self.__log(self.logger.info, *msg)

    def warning(self, *msg):
        self.__log(self.logger.warning, *msg)

    def error(self, *msg):
        self.__log(self.logger.error, *msg)

    def critical(self, *msg):
        self.__log(self.logger.critical, *msg)

    def log(self, level, msg):
        self.logger.log(level, msg)

    def setLevel(self, level):
        self.logger.setLevel(level)

    def disable(self):
        logging.disable(50)

    def __log(self, func, *msg):
        func(" ".join(map(str, msg)))
        
    def close(self):
        self.logger.removeHandler(self.streamhandler)
        self.streamhandler.flush()
        self.streamhandler.close()

        if self.filehandler and self.logfile:
            self.logger.removeHandler(self.filehandler)
            self.filehandler.flush()
            self.filehandler.close()
        
logger = Logger()