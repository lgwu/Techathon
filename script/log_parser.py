#-------------------------------------------------------------------------------
# Name:        log_parser.py
# Purpose:
#
# Author:      lgwu
#
# Created:     07/03/2014
# Copyright:   (c) lgwu 2014
# Licence:     <your licence>
#-------------------------------------------------------------------------------
import re
import os
import sys
import glob
import csv

class POILogRecord(object):
    """
        class comment
    """

    def __init__(self):
        self.date = ''
        self.timestamp = ''

        self.request_id = ''

        self.query = ''

        # category param
        self.category_hierarchy_id = -1
        self.category_version = ''
        self.category_id = ''

        self.search_type = ''

        # search area
        self.area_type = ''
        self.anchor = ''
        self.city = ''
        self.country_code = ''
        self.postal_code = ''
        self.province = ''
        self.radius = ''
        self.route_points = ''


        # content string
        self.device_carrier = ''
        self.product = ''
        self.carrier = ''


        self.user_id = -1

        self.is_debug = ''


    def get_fields(self):
        items = [
            self.date,
            self.timestamp,
            self.request_id,
            self.query,

            self.category_hierarchy_id,
            self.category_version,
            self.category_id,

            self.search_type,

            self.area_type,
            self.anchor,
            self.city,
            self.country_code,
            self.postal_code,
            self.province,
            self.radius,
            self.route_points,

            self.device_carrier,
            self.product,
            self.carrier,

            self.user_id,

            self.is_debug,
        ]

        return items

    def __str__(self):
        fields = self.get_fields()

        return '%s\n' % '\t'.join(map(str, fields))

class RecordParser(object):
    """
        class comment
    """
    REQUEST_KEYS = [
        'PoiQuery',
        'CategoryParam',
        'SearchType',
        'SearchArea',
        'SortType',
        'PoiStartIndex',
        'PoiNumber',
        'MinReturnedItems',
        'NeedSponsoredPois',
        'SponsorListingStartIndex',
        'SponsorListingNumber',
        'OnlyMostPopular',
        'NeedUserPreviousRating',
        'NeedUserGeneratePois',
        'NeedHighlyRelevantPois',
        'ContextString',
        'UserId',
        'TransactionId',
        'ServiceAgent',
        'UgcPreference',
        'AutoExpandSearchRadius',
        'IsDebugRequest',
        'ClientName',
        'ClientVersion',
        'ContextProperty',
     ]

    CONTENT_KEYS = [
        'poidataset',
        'deviceCarrier',
        'product',
        'supportTouch',
        'program_code',
        'region',
        'version',
        'XNAV_TIMESTAMP',
        'carrier',
        'requestor',
        'application',
        'c-server url',
        'needreview',
        'adengine',
        'dataset',
        'c-server class',
        'searchunit',
        'login',
        'needsponsor',
        'poicountrylist',
        'GENERATE_LANE_INFO',
        'adtype',
        'device',
        'locale',
        'poiFinderVersion',
        'isAndroid',
    ]


    def __init__(self):
        self.start = 0

    @staticmethod
    def parse(record):
        log = POILogRecord()

        log.timestamp = RecordParser._parse_timestamp(record)
        log.request_id = RecordParser._parse_request_id(record)

        req_kvs = RecordParser._parse_request(record)
        if not req_kvs:
            return None

        log.query = req_kvs.get('PoiQuery', '')

        log.is_debug = req_kvs.get('IsDebugRequest', '')

        category_param = req_kvs.get('CategoryParam', '')
        if not category_param:
            print 'Invalid category param [%s]' % record.strip()

        RecordParser._parse_category_param(category_param, log)

        search_area = req_kvs.get('SearchArea', '')
        RecordParser._parse_search_area(search_area, log)

        content_string = req_kvs.get('ContextString', '')
        RecordParser._parse_content_string(content_string, log)

        log.user_id = req_kvs.get('UserId', -1)

        return log


    @staticmethod
    def _parse_timestamp(record):
        return record[:8]

    @staticmethod
    def _parse_request_id(record):
        KEY = 'RequestId'

        start = record.find(KEY)
        end = record.find(',', start)

        return record[start:end].split('=')[1][1:-1]

    @staticmethod
    def _get_request_string(record):
        KEY = 'PoiRequest'

        start = record.find(KEY)

        try:
            return record[start:].split('=',1)[1][1:-1]
        except:
            print 'Invalid record [%s]' % record

    @staticmethod
    def _parse_request(record):
        request = RecordParser._get_request_string(record)
        if not request:
            return None

##        key_starts = [request.find(key) for key in RecordParser.REQUEST_KEYS]
##        key_ends = [item[0] + len(item[1]) for item in zip(key_starts, RecordParser.REQUEST_KEYS)]
##
##        val_starts = [pos+1 for pos in key_ends]
##        val_ends = [pos -1 for pos in key_starts]
##
##        del val_ends[0]
##        val_ends.append(len(request))
##
##        vals = [request[pos[0]:pos[1]] for pos in zip(val_starts, val_ends)]
##
##        return dict(zip(RecordParser.REQUEST_KEYS, vals))

        return RecordParser._parse_kvs(request, RecordParser.REQUEST_KEYS)

    @staticmethod
    def _parse_category_param(category_param, log):
        KEYS = ['CategoryHierarchyId', 'CategoryVersion', 'CategoryId']

        if category_param.startswith('[') and category_param.endswith(']'):
            category_param = category_param[1:-1]

        kvs =  RecordParser._parse_kvs(category_param, KEYS)

        log.category_hierarchy_id = kvs.get('CategoryHierarchyId', -1)
        log.category_version = kvs.get('CategoryVersion', '')
        log.category_id = kvs.get('CategoryId', '[]')[1:-1]

    @staticmethod
    def _parse_search_area(search_area, log):
        KEYS = ['AreaType', 'Anchor', 'City', 'CountryCode', 'CountryList', 'GeoCodedAddress', 'PostalCode', 'Province', 'RadiusInMeter', 'RawAddressLine', 'RoutePoints']

        if search_area.startswith('[') and search_area.endswith(']'):
            search_area = search_area[1:-1]

        kvs = RecordParser._parse_kvs(search_area, KEYS)

        log.area_type = kvs.get('AreaType', '')
        log.anchor = kvs.get('Anchor', '[]')[1:-1]
        log.city =  kvs.get('City', '')
        log.country_code =  kvs.get('CountryCode', '')
        log.postal_code =  kvs.get('PostalCode', '')
        log.province =  kvs.get('Province', '')
        log.radius =  kvs.get('RadiusInMeter', '')
        log.route_points = kvs.get('RoutePoints', '')


    @staticmethod
    def _parse_content_string(content_string, log):
        KEYS = RecordParser.CONTENT_KEYS

        if content_string.startswith('[') and content_string.endswith(']'):
            content_string = content_string[1:-1]

        kvs = RecordParser._parse_kvs(content_string, KEYS)
##        try:
##            kvs = dict([i.split('=',1) for i in content_string.split(';')])
##        except:
##            print content_string

        log.device_carrier = kvs.get('deviceCarrier', '')
        log.product = kvs.get('product','')
        log.device = kvs.get('device','')

    @staticmethod
    def _parse_kvs(string, keys):
        pos_keys = [(string.find(key), key) for key in keys]
        pos_keys = [pk for pk in pos_keys if pk[0] >= 0]
        pos_keys.sort()

        key_starts = [pk[0] for pk in pos_keys]
        key_ends = [pk[0] + len(pk[1]) for pk in pos_keys]

        val_starts = [pos+1 for pos in key_ends]
        val_ends = [pos -1 for pos in key_starts]

        try:
            del val_ends[0]
            val_ends.append(len(string))
        except:
            print string

        vals = [string[pos[0]:pos[1]] for pos in zip(val_starts, val_ends)]

        return dict(zip([pk[1] for pk in pos_keys], vals))

class LogParser(object):
    """
        class comment
    """

    def __init__(self, log_path, data_path):
        self.log_path = log_path
        self.data_path = data_path

    def parse_log_file(self, log_file, data_file):
        date = self._get_date(log_file)

##        with open(data_file, 'w') as ofs:
##
##            for line in open(log_file):
##                log = RecordParser.parse(line)
##                if not log:
##                    continue
##                log.date = date
##                ofs.write(str(log))

        with open(data_file, 'wb') as csvfile:
            spamwriter = csv.writer(csvfile, delimiter='\t',
                                    quotechar='"', quoting=csv.QUOTE_MINIMAL)

            for line in open(log_file):
                log = RecordParser.parse(line)

                if not log: continue

                spamwriter.writerow(log.get_fields())

    def parse_log(self):
        log_files = []

        if os.path.isfile(self.log_path):
            log_files.append(self.log_path)
        else:
            log_files.extend(glob.glob(os.path.join(self.log_path, '*')))

        log_files =[log_file for log_file in log_files if os.path.isfile(log_file)]

        for log_file in log_files:
            data_file = os.path.join(self.data_path, os.path.basename(log_file))
            self.parse_log_file(log_file, data_file)

    def _get_date(self, log_file):
        for m in re.finditer('\d{4}-\d{2}-\d{2}', log_file):
            return m.group()
        return ''

def main():
    #record = '00:00:24 RequestId=[1380610824642@Thread-34],PoiRequest=[PoiQuery=ohsu hospital,CategoryParam=[CategoryHierarchyId=1,CategoryVersion=YP50,CategoryId=[-1]],SearchType=FOR_GENERAL_POI,SearchArea=[AreaType=ANCHOR_AND_RADIUS,Anchor=[45.48304,-122.68066],City=,CountryCode=US,CountryList=[],GeoCodedAddress=,PostalCode=,Province=,RadiusInMeter=80000,RawAddressLine=,RoutePoints=[]],SortType=BY_RANKING,PoiStartIndex=0,PoiNumber=10,MinReturnedItems=-1,NeedSponsoredPois=true,SponsorListingStartIndex=0,SponsorListingNumber=0,OnlyMostPopular=false,NeedUserPreviousRating=true,NeedUserGeneratePois=true,NeedHighlyRelevantPois=true,ContextString=[poidataset=TA;deviceCarrier=BOOST;product=ANDROID;supportTouch=TRUE;program_code=SCOUTUSPROG;region=US;version=7.5.1;XNAV_TIMESTAMP=1380607612396;carrier=SCOUTUSPROG;requestor=tnclient;application=SCOUT_PAID;c-server url=tn7x-poi.telenav.com;needreview=FALSE;adengine=ALL;dataset=TeleAtlas;c-server class=CServer6x_HTTP;searchunit=KM;login=3102135963;needsponsor=TRUE;poicountrylist=US,CA;GENERATE_LANE_INFO=false;adtype=SPONSORED_TEXT,MERCHANT_CONTENT,COUPON,MENU;device=d2vmu;locale=en_US;poiFinderVersion=YP50;isAndroid=FALSE],UserId=85198978,TransactionId=1380610785993,ServiceAgent=[Vendor=TELENAV,Service=TELENAV_COSE,DataSet=TA],UgcPreference=,AutoExpandSearchRadius=true,IsDebugRequest=false,ClientName=Onebox,ClientVersion=0.0.1,ContextProperty=[KEY_IS_PARTIALLY_CLOSE_MULTIFIELD=true]]'
    #log = RecordParser.parse(record)
    record = '09:12:48 RequestId=[1380643968443@Thread-27],PoiRequest=[PoiQuery=Gupton William E MD,CategoryParam=,SearchType=FOR_GENERAL_POI,SearchArea=[AreaType=CITY_STATE,Anchor=,City=SACRAMENTO,CountryCode=US,CountryList=[],GeoCodedAddress=,PostalCode=,Province=CA,RadiusInMeter=-2147483648,RawAddressLine=,RoutePoints=[]],SortType=BY_RANKING,PoiStartIndex=0,PoiNumber=50,MinReturnedItems=-2147483648,NeedSponsoredPois=false,SponsorListingStartIndex=0,SponsorListingNumber=1,OnlyMostPopular=false,NeedUserPreviousRating=false,NeedUserGeneratePois=false,NeedHighlyRelevantPois=false,ContextString=[needsponsor=FALSE;searchunit=KM;poidataset=TA],UserId=12345678,TransactionId=1380643968427,ServiceAgent=[Vendor=TELENAV,Service=TELENAV_COSE,DataSet=TA],UgcPreference=,AutoExpandSearchRadius=true,IsDebugRequest=true,ClientName=PoiDemo,ClientVersion=1.0,ContextProperty=]'
    log = RecordParser.parse(record)

    #print log

    parser = LogParser(sys.argv[1], sys.argv[2])
    parser.parse_log()


if __name__ == '__main__':
    main()
